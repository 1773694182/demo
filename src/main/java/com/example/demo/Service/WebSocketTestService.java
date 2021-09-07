package com.example.demo.Service;

import com.example.demo.Service.GroupService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * websocket服务端,多例的，一次websocket连接对应一个实例
 *  @ServerEndpoint 注解的值为URI,映射客户端输入的URL来连接到WebSocket服务器端
 */
@Component
@Service
//@ServerEndpoint("/")
@ServerEndpoint(value="/{name}",encoders = {ServerEncoder.class})
public class WebSocketTestService {
    /** WebSocket无法注入Bean，Service等，要在Config文件内进行 @Autowired或其他注入*/
    public static GroupService groupService;
    /** 用来记录当前在线连接数。设计成线程安全的。*/
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /** 用于保存uri对应的连接服务，{uri:WebSocketService}，设计成线程安全的 */
    private static ConcurrentHashMap<String, WebSocketTestService> webSocketServerMAP = new ConcurrentHashMap<>();
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
//     * @param name
     * @throws IOException
     */
    @OnOpen
    public void onOpen( Session session, @PathParam("name") String name) throws IOException {
        this.session = session;
        this.name = name;
        this.uri = session.getRequestURI().toString();
        WebSocketTestService WebSocketTestService = webSocketServerMAP.get(uri);
        if(WebSocketTestService != null){ //同样业务的连接已经在线，则把原来的挤下线。
            WebSocketTestService.session.getBasicRemote().sendText(uri + "重复连接被挤下线了");
            WebSocketTestService.session.close();//关闭连接，触发关闭连接方法onClose()
        }
        webSocketServerMAP.put(uri, this);//保存uri对应的连接服务
        addOnlineCount(); // 在线数加1
//        System.out.println(this);
        System.out.println(this + "有新连接加入！当前在线连接数：" + getOnlineCount());
//        System.out.println(groupService.GetMessage(Integer.parseInt(name)));
    }

    /**
     * 连接关闭时触发，注意不能向客户端发送消息了
     * @throws IOException
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketServerMAP.remove(uri);//删除uri对应的连接服务
        reduceOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线连接数" + getOnlineCount());
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
    public void onMessage(String message,Session session) throws IOException, EncodeException {
        System.out.println(message);
        String[] arr=message.split(",",2);
        this.toName=arr[0];
        StringBuilder receiverUri = new StringBuilder("ws://localhost:8080/");
        receiverUri.append(toName);//发送信息路径
        WebSocketTestService WebSocketTestService = webSocketServerMAP.get(receiverUri.toString());
        if(WebSocketTestService != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(System.currentTimeMillis());
            Map map=new HashMap();
            map.put("user_id",name);
            map.put("message",arr[1]);
            map.put("date",dateString);
            WebSocketTestService.session.getBasicRemote().sendObject(map);
        }
        groupService.SendMessage(toName,name,arr[1]);
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