function CreateTalk(){
    $.ajax({
        url:"/GetMessageByID",
        data:{"user_id":to_user_id },
        type:"post",
        success:function (data){
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
            for (var i=0;i<data.length;i++){
                // console.log(data)
                // console.log(data[i].user_id)
                // console.log(data[i].date)
                // console.log(data[i].message)
                var box=document.getElementById("message")
                var p=document.createElement("div")
                var t=document.createElement("p")
                t.innerText=data[i].date
                t.setAttribute("class","date")
                p.appendChild(t)
                t=document.createElement("label")
                t.setAttribute("class","UserMessage")
                t.innerText=data[i].message
                p.appendChild(t)
                box.appendChild(p)
            }
        }
    })
}