var websocket = null;

var userId = null;

//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
    websocket = new WebSocket("ws://localhost:8080/1/2");
} else {
    alert("Don't support websocket!")
}

//连接发生错误的回调方法
websocket.onerror = function(){
    alert("Connect error!");
};

//连接成功建立的回调方法
websocket.onopen = function(event){
    setMessageInnerHTML("连接已建立！");
}

//接收到消息的回调方法
websocket.onmessage = function(event){
    var result = event.data
    var ob = JSON.parse(result)
    //判断用户状态
    if(ob.state != undefined && ob.state != "success"){
        setMessageInnerHTML("非法连接！");
        websocket.close();
    }

    //判断是否有消息
    if(ob.msg != undefined){
        setMessageInnerHTML(ob.msg);
    }
}

//连接关闭的回调方法
websocket.onclose = function(){
    setMessageInnerHTML("close");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
    websocket.close();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML){
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//关闭连接
function closeWebSocket(){
    websocket.close();
}

//发送消息
function send(){
    var sendMsg = $("#sendMsg").val();
    setMessageInnerHTML("我 ：" + sendMsg)
    websocket.send(sendMsg);
    $("#sendMsg").val("");
}
