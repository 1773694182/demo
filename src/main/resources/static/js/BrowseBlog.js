function GetCommentNumber() {
    var blog_id=document.getElementById("blog_id")
    console.log("getCommentNumber")
    $.ajax({
        url:"/ToGetCommentNumber",
        type:"post",
        contentType:"application/x-www-form-urlencoded",
        data: {"blog_id":blog_id.value},
        async: false,
        success:function (data){
            document.getElementById("comment_number").innerText=data
        }
    })
}
function postReplay(id){
    var x=$("#comment_form"+id).serializeArray()
    creatReplayForm(x)
    GetCommentNumber()
}
function postReplayReplay(id){
    var x=$("#replay_form"+id).serializeArray()
    creatReplayForm(x)
    GetCommentNumber()
}
function creatReplayForm(x) {
    $.ajax({
        url:"/PostReplay",
        type:"post",
        contentType:"application/json; charset=utf-8",
        data: JSON.stringify(x),
        async: false,
        success:function (data){
            console.log("OK")
            remove_textarea("replay_textarea")
            remove_textarea("comment_textarea")
            remove_button()
            data=JSON.parse(data)
            var box=document.getElementById("comment_form"+data.comment_id).parentNode
            var t=document.createElement("div")
            t.setAttribute("class","replay")
            box.appendChild(t)
            var p=document.createElement("div")
            t.appendChild(p)
            t=document.createElement("form")
            t.setAttribute("tabindex","1")
            t.setAttribute("id","replay_form"+data.comment_id)
            p.appendChild(t)
            var k=document.createElement("input")
            k.setAttribute("name","comment_id")
            k.setAttribute("value",data.comment_id)
            k.setAttribute("hidden","hidden")
            t.appendChild(k)
            k=document.createElement("input")
            k.setAttribute("name","to_user_id")
            k.setAttribute("value",data.to_user_id)
            k.setAttribute("hidden","hidden")
            t.appendChild(k)
            k=document.createElement("input")
            k.setAttribute("name","user_id")
            k.setAttribute("value",data.user_id)
            k.setAttribute("hidden","hidden")
            t.appendChild(k)
            k=document.createElement("div")
            k.setAttribute("id","replay"+data.comment_id)
            t.appendChild(k)
            t=document.createElement("a")
            t.setAttribute("class","name")
            t.setAttribute("href","/GetUserInfo?user_id="+data.user_id)
            k.appendChild(t)
            p=document.createElement("label")
            p.innerText=data.user_name
            t.appendChild(p)
            t=document.createElement("label")
            t.innerText="\xa0\xa0回复\xa0\xa0"
            k.appendChild(t)
            t=document.createElement("a")
            t.setAttribute("class","name")
            t.setAttribute("href","/GetUserInfo?user_id="+data.to_user_id)
            k.appendChild(t)
            p=document.createElement("label")
            p.innerText=data.to_user_name
            t.appendChild(p)
            t=document.createElement("label")
            t.innerText="\xa0\xa0:\xa0"
            k.appendChild(t)
            t=document.createElement("span")
            t.innerText=data.content
            k.appendChild(t)
            t=document.createElement("button")
            t.setAttribute("type","button")
            t.setAttribute("onclick","getReplayFrom("+data.comment_id+")")
            k.appendChild(t)
            p=document.createElement("label")
            p.innerText="回复"
            t.appendChild(p)
        }
    })
}
function postComment(){
    var x=$("#comment_form").serializeArray()
    console.log(typeof x)
    $.ajax({
        url:"/PostComment",
        type:"post",
        async: false,
        contentType:"application/json; charset=utf-8",
        data: JSON.stringify(x),
        success:function (data){
            data=JSON.parse(data)
            remove_textarea("replay_textarea")
            remove_textarea("comment_textarea")
            remove_button()

            document.getElementById("comment_number").innerText=data.comment_number
            document.getElementById("comment_blog_textarea").value=""

            var box=document.getElementById("view-comment")

            var t=document.createElement("div")
            t.setAttribute("class","comment")
            box.appendChild(t)

            var p=document.createElement("div")
            t.appendChild(p)

            t=document.createElement("form")
            t.setAttribute("tabindex","1")
            t.setAttribute("id","comment_form"+data.comment_id)
            p.appendChild(t)

            var k=document.createElement("input")
            k.setAttribute("name","user_id")
            k.setAttribute("value","1")
            k.setAttribute("hidden","hidden")
            t.appendChild(k)

            k=document.createElement("input")
            k.setAttribute("name","to_user_id")
            k.setAttribute("value","1")
            k.setAttribute("hidden","hidden")
            t.appendChild(k)

            k=document.createElement("input")
            k.setAttribute("name","comment_id")
            k.setAttribute("value",data.comment_id)
            k.setAttribute("hidden","hidden")
            t.appendChild(k)

            k=document.createElement("div")
            k.setAttribute("id","comment"+data.comment_id)
            t.appendChild(k)

            t=document.createElement("a")
            t.setAttribute("class","name")
            t.setAttribute("href","/GetUserInfo?user_id="+data.user_id)
            k.appendChild(t)

            p=document.createElement("label")
            p.innerText=data.user_name
            t.appendChild(p)

            t=document.createElement("label")
            t.innerText="\xa0\xa0:\xa0"
            k.appendChild(t)

            t=document.createElement("span")
            t.innerText=data.content
            k.appendChild(t)

            t=document.createElement("button")
            t.setAttribute("type","button")
            t.setAttribute("onclick","getCommentFrom("+data.comment_id+")")
            k.appendChild(t)

            p=document.createElement("label")
            p.innerText="回复"
            t.appendChild(p)
        }
    })
}
function getCommentFrom(comment_id) {
    var box=document.getElementById("comment"+comment_id)
    remove_textarea("replay_textarea")
    remove_textarea("comment_textarea")
    remove_button()
    if(!document.getElementById("comment_textarea")){
        var p=document.createElement("div")
        p.setAttribute("class","comment_textarea")
        p.setAttribute("id","comment_textarea")
        p.setAttribute("tabindex",0)
        box.appendChild(p)

        var box=document.getElementById("comment_textarea")
        var t=document.createElement("textarea")
        t.setAttribute("class","replay-input form-control")
        t.setAttribute("id","comment_textarea_input")
        t.setAttribute("name","content")
        box.appendChild(t)

        var t=document.createElement("button")
        t.setAttribute("id","cancel")
        t.setAttribute("type","button")
        t.setAttribute("onclick","remove_textarea('comment_textarea')")
        t.innerText="取消评论"
        box.appendChild(t)

        var t=document.createElement("button")
        t.setAttribute("id","replay_button")
        t.setAttribute("type","button")
        t.setAttribute("onclick","postReplay("+comment_id+")")
        t.innerText="发布评论"
        box.appendChild(t)
    }
    document.getElementById("comment_textarea_input").focus()
}
function getReplayFrom(replay_id) {
    var box=document.getElementById("replay"+replay_id)
    remove_textarea("replay_textarea")
    remove_textarea("comment_textarea")
    remove_button()
    if(!document.getElementById("replay_textarea")) {
        var p=document.createElement("div")
        p.setAttribute("class","replay_textarea")
        p.setAttribute("id","replay_textarea")
        p.setAttribute("tabindex",0)
        box.appendChild(p)

        var box=document.getElementById("replay_textarea")
        var t=document.createElement("textarea")
        t.setAttribute("class","replay-input form-control")
        t.setAttribute("id","replay_textarea_input")
        t.setAttribute("name","content")
        box.appendChild(t)

        var t=document.createElement("button")
        t.setAttribute("id","cancel")
        t.setAttribute("type","button")
        t.setAttribute("onclick","remove_textarea('replay_textarea')")
        t.innerText="取消评论"
        box.appendChild(t)

        var t=document.createElement("button")
        t.setAttribute("id","replay_button")
        t.setAttribute("type","button")
        t.setAttribute("onclick","postReplayReplay("+replay_id+")")
        t.innerText="发布评论"
        box.appendChild(t)
    }
    document.getElementById("replay_textarea_input").focus()
}
function remove_textarea(id) {
    console.log(1)
    $("#"+id).remove();
}
function create_button(id) {
    remove_textarea("replay_textarea")
    remove_textarea("comment_textarea")
    remove_button()
    var box=document.getElementById(id)
    if(!document.getElementById("cancel_replay")){
        var p=document.createElement("button")
        p.setAttribute("id","cancel_replay")
        p.setAttribute("onclick","remove_button()")
        p.setAttribute("type","button")
        p.innerText="取消评论"
        box.appendChild(p)

        var t=document.createElement("button")
        t.setAttribute("id","replay_button")
        t.setAttribute("type","button")
        t.setAttribute("onclick","postComment()")
        t.innerText="发布评论"
        box.appendChild(t)
    }
}
function remove_button() {
    if(document.getElementById("replay_button")){
        var box=document.getElementById("replay_button")
        box.parentNode.removeChild(box)
        box=document.getElementById("cancel_replay")
        box.parentNode.removeChild(box)
    }
}
