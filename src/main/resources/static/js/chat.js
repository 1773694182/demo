var websocket = null;

//判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
if ('WebSocket' in window) {
    var url="ws://localhost:8080/"+document.getElementById("user_id").value
    websocket = new WebSocket(url);
} else {
    alert('Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function() {
    setMessageInnerHTML("error");
};

//连接成功建立的回调方法
websocket.onopen = function(event) {
    // setMessageInnerHTML("open");
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
    setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
websocket.onclose = function() {
    // setMessageInnerHTML("close");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    websocket.close();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//关闭连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = $("#sendMsg").val();
    var arr=["2",message]
    websocket.send(arr);
    // websocket.send(message);
}
