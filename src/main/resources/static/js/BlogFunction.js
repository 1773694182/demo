function DeleteBlog(blog_id) {
    var t=document.getElementById(blog_id)
    t.parentNode.removeChild(t)
}
function ToDeleteBlog(blog_id){
    $.ajax({
        url:"/DeleteBlog",
        type:"post",
        data:{"blog_id":blog_id},
        async: false,
        success:function () {
            DeleteBlog(blog_id)
        }
    })
}

function ToPostBlog(blog_id) {
    $.ajax({
        url:"/PostBlogFromDraft",
        type:"post",
        data:{"blog_id":blog_id},
        async: false,
        success:function () {
            DeleteBlog(blog_id)
        }
    })
}
