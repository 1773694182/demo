function CreateTalk(){
    $.ajax({
        url:"/GetMessageByID",
        data:{"user_id":to_user_id },
        type:"post",
        success:function (data){
            console.log(data)
            var name=document.getElementById("name"+to_user_id).innerText
            var box=document.getElementById("talk")

            var p=document.createElement("div")
            p.setAttribute("class","name")
            p.setAttribute("name","name")
            p.innerText=name
            box.appendChild(p)

            var p=document.createElement("div")
            p.setAttribute("class","ShowMessage")
            p.setAttribute("id","message")
            box.appendChild(p)

            var p=document.createElement("div")
            p.setAttribute("class","EnterMessage")

            var t=document.createElement("textarea")
            t.setAttribute("id","sendMsg")
            t.setAttribute("class","EnterMessage form-control")
            t.setAttribute("placeholder","请输入内容")
            p.appendChild(t)

            t=document.createElement("button")
            t.setAttribute("onclick","send()")
            t.setAttribute("type","button")
            t.innerText="发送"
            p.appendChild(t)

            box.appendChild(p)
            for (var i=data.length-1;i>=0;i--){
                CreateMessage(data[i].user_id,data[i].date,data[i].message)
            }
            var scrollTarget = document.getElementById("message");
            scrollTarget.scrollTop=scrollTarget.scrollHeight;
        }
    })
}
function CreateMessage(user_id,date,message) {
    var box=document.getElementById("message")

    var p=document.createElement("div")
    p.setAttribute("class","MessageInfo")

    var k=document.createElement("div")
    k.setAttribute("class","date")
    var t=document.createElement("span")
    t.innerText=date
    k.appendChild(t)
    p.appendChild(k)

    var k=document.createElement("div")
    t=document.createElement("span")
    if (user_id==account)
        k.setAttribute("class","MyMessage")
    else
        k.setAttribute("class","UserMessage")
    var m=document.createElement("div")
    m.setAttribute("style","background-color: #0e90d2;")
    t.innerText=message
    m.appendChild(t)
    k.appendChild(m)
    p.appendChild(k)

    console.log(message)
    box.appendChild(p)
}
function GetDate() {
    var date=null
    $.ajax({
        url:"/GetDate",
        type:"post",
        async:false,
        success:function (data){
            date=data
        }
    })
    console.log(date)
    return date
}