var websocket = null;
var to_user_id=null
//判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
if ('WebSocket' in window) {
    var url="ws://localhost:8080/"+account
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
    console.log(to_user_id)
    var message = $("#sendMsg").val();
    var arr=[to_user_id,message]
    websocket.send(arr);
    var date=null
    $.ajax({
        url:"/GetDate",
        type:"post",
        async:false,
        success:function (data){
            date=data
            console.log(data)
            console.log(date)
        }
    })
    var box=document.getElementById("message")
    var p=document.createElement("div")
    var t=document.createElement("p")
    console.log(date)
    t.innerText=date
    t.setAttribute("class","date")
    p.appendChild(t)
    t=document.createElement("label")
    t.setAttribute("class","MyMessage")
    t.innerText=message
    p.appendChild(t)
    box.appendChild(p)
    // websocket.send(message);
}
function ToChat(x) {
    to_user_id=x
    console.log(to_user_id)
    $("#talk").empty();
    CreateTalk()
}