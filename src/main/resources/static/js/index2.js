$.ajax({
    type:"get",
    url:"/GetAllBlog",
    async: false,
    success:function (data) {
        window.location.href="/GetAllBlog"
    },
    error:function (data){
        alert("error")
    }
})