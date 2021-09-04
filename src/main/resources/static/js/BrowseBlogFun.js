function like() {
    var blog_id=document.getElementById("blog_id")
    $.ajax({
        url:"/LikeBlog",
        data:{"blog_id":blog_id.value},
        type:"post",
        async: false,
        success:function (data){
            if (data=="-1")
                layer.msg("您已经赞过了！")
            else{
                document.getElementById("like_number").innerText=data
                document.getElementById("like_number2").innerText=data
            }
        }
    })
}
function collection() {
    var blog_id=document.getElementById("blog_id")
    $.ajax({
        url: "/CollectionBlog",
        data:{"blog_id":blog_id.value},
        type:"post",
        async: false,
        success:function(data){
            if(data=="-1"){
                layer.msg("取消收藏成功！")
            }
            else {
                layer.msg("收藏成功！")
                document.getElementById("collection_number").innerText=data
                document.getElementById("collection_number2").innerText=data
            }
        }
    })
}