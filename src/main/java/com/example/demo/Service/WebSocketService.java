package com.example.demo.Service;

import com.example.demo.Service.GroupService;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/**
 * websocket服务端,多例的，一次websocket连接对应一个实例
 *  @ServerEndpoint 注解的值为URI,映射客户端输入的URL来连接到WebSocket服务器端
 */
@Component
@Service
@ServerEndpoint("/{name}/{toName}")
public class WebSocketService {
  /** WebSocket无法注入Bean，Service等，要在Config文件内进行 @Autowired或其他注入*/
  public static GroupService groupService;
  /** 用来记录当前在线连接数。设计成线程安全的。*/
  private static AtomicInteger onlineCount = new AtomicInteger(0);
  /** 用于保存uri对应的连接服务，{uri:WebSocketService}，设计成线程安全的 */
  private static ConcurrentHashMap<String, WebSocketService> webSocketServerMAP = new ConcurrentHashMap<>();
  private Session session;// 与某个客户端的连接会话，需要通过它来给客户端发送数据
  private String name; //客户端消息发送者
  private String toName; //客户端消息接受者
  private String uri; //连接的uri

  public static void BroadCastInfo(String message) {
  }

  /**
   * 连接建立成功时触发，绑定参数
   * @param session
   *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
   * @param name
   * @param toName
   * @throws IOException
   */
  @OnOpen
  public void onOpen(Session session, @PathParam("name") String name, @PathParam("toName") String toName) throws IOException {
    this.session = session;
    this.name = name;
    this.toName = toName;
    this.uri = session.getRequestURI().toString();

    toName="/"+toName;
    uri=uri.replace(toName,"");
    WebSocketService webSocketServer = webSocketServerMAP.get(uri);
    if(webSocketServer != null){ //同样业务的连接已经在线，则把原来的挤下线。
      webSocketServer.session.getBasicRemote().sendText(uri + "重复连接被挤下线了");
      webSocketServer.session.close();//关闭连接，触发关闭连接方法onClose()
    }
    webSocketServerMAP.put(uri, this);//保存uri对应的连接服务
    addOnlineCount(); // 在线数加1
//    System.out.println(this + "有新连接加入！当前在线连接数：" + getOnlineCount());
    System.out.println(groupService.GetMessage(Integer.parseInt(name)));
  }

  /**
   * 连接关闭时触发，注意不能向客户端发送消息了
   * @throws IOException
   */
  @OnClose
  public void onClose() throws IOException {
    webSocketServerMAP.remove(uri);//删除uri对应的连接服务
    reduceOnlineCount(); // 在线数减1
//    System.out.println("有一连接关闭！当前在线连接数" + getOnlineCount());
  }

  /**
   * 收到客户端消息后触发,分别向2个客户端（消息发送者和消息接收者）推送消息
   *
   * @param message
   *            客户端发送过来的消息
   * @param session
   *            可选的参数
   * @throws IOException
   */
  @OnMessage
  public void onMessage(String message,Session session) throws IOException {
    //服务器向消息发送者客户端发送消息
//    System.out.println("onMessage");
    this.session.getBasicRemote().sendText("To" + toName + ":" + message);
    //指定员工
    //获取消息接收者的客户端连接
    StringBuilder receiverUri = new StringBuilder("ws://localhost:8080/");
//    System.out.println(webSocketServerMAP);
//    System.out.println(receiverUri+name);
    receiverUri.append(toName);//发送信息路径
//    System.out.println(receiverUri);
    WebSocketService webSocketServer = webSocketServerMAP.get(receiverUri.toString());
    if(webSocketServer != null){
      webSocketServer.session.getBasicRemote().sendText("From" +name + ":" + message);
    }
    else {
      groupService.SendMessage(toName,name,message);
      this.session.getBasicRemote().sendText("SendSuccess");
    }
  }

  /**
   * 通信发生错误时触发
   *
   * @param error
   */
  @OnError
  public void onError(Throwable error) {
    System.out.println("发生错误");
    error.printStackTrace();
  }

  /**
   * 向客户端发送消息
   * @param message
   * @throws IOException
   */
  public void sendMessage(String message) throws IOException {
    this.session.getBasicRemote().sendText(message);
  }

  /**
   * 获取在线连接数
   * @return
   */
  public static int getOnlineCount() {
    return onlineCount.get();
  }

  /**
   * 原子性操作，在线连接数加一
   */
  public static void addOnlineCount() {
    onlineCount.getAndIncrement();
  }

  /**
   * 原子性操作，在线连接数减一
   */
  public static void reduceOnlineCount() {
    onlineCount.getAndDecrement();
  }
}