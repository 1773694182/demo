function like() {
    var blog_id=document.getElementById("blog_id")
    $.ajax({
        url:"/LikeBlog",
        data:{"blog_id":blog_id.value},
        type:"post",
        async: false,
        success:function (data){
            if (data.flag=="-1")
                layer.msg("您已经赞过了！")
            else{
                document.getElementById("like_number").innerText=data.Like_number
                document.getElementById("like_number2").innerText=data.Like_number
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
            if(data.flag==-1){
                layer.msg("取消收藏成功！")
                document.getElementById("collection_number").innerText=data.collection_number
                document.getElementById("collection_number2").innerText=data.collection_number
            }
            else {
                layer.msg("收藏成功！")
                document.getElementById("collection_number").innerText=data.collection_number
                document.getElementById("collection_number2").innerText=data.collection_number
            }
        }
    })
}
function DeleteComment(comment_id) {
    $.ajax({
        url:"/DeleteComment",
        type:"post",
        data:{"comment_id":comment_id},
        success:function (){
            layer.msg("删除成功")
        }
    })
}
function DeleteReplay(replay_id) {
    $.ajax({
        url:"/DeleteReplay",
        type:"post",
        data:{"replay_id":replay_id},
        success:function (){
            layer.msg("删除成功")
        }
    })
}
function ReportComment(comment_id) {
    $.ajax({
        url:"/ReportComment",
        type:"post",
        data:{"comment_id":comment_id},
        success:function (){
            layer.msg("举报成功")
        }
    })
}
function ReportReplay(replay_id) {
    $.ajax({
        url:"/ReportReplay",
        type:"post",
        data:{"replay_id":replay_id},
        success:function (){
            layer.msg("举报成功")
        }
    })
}