function UpdateBlog() {
    var box=$("#post").serializeArray()
    box.push({"name":"state","value":0})
    console.log(box)
    $.ajax({
        url:"/UpdateBlog",
        type:"post",
        async: false,
        contentType:"application/json; charset=utf-8",
        data:JSON.stringify(box),
        success:function (data){
            window.location.href="GetUserInfo"
        }
    })
}