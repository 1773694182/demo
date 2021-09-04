function PostBlog(x) {
    var box=$("#post").serializeArray()
    box.push({"name":"state","value":x})
    console.log(box)
    $.ajax({
        url:"/PostBlog",
        type:"post",
        async: false,
        contentType:"application/json; charset=utf-8",
        data:JSON.stringify(box),
        success:function (data){
            if (x==0){
                alert("发布成功，等待审核")
                // window.href="index2"
            }
            else if(x==1){
                alert("保存成功，可前往草稿箱查看")
            }
        }
    })
}
