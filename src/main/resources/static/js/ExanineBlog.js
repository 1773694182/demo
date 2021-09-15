function ExamineBlogSuccess(blog_id){
    $.ajax({
        url:"",
        data:{"blog_id":blog_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function ExamineBlogFailed(blog_id) {
    $.ajax({
        url:"",
        data:{"blog_id":blog_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function ExamineCommentSuccess(comment_id){
    $.ajax({
        url:"",
        data:{"comment_id":comment_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function ExamineCommentFailed(comment_id) {
    $.ajax({
        url:"",
        data:{"comment_id":comment_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function ExamineReplaySuccess(replay_id){
    $.ajax({
        url:"",
        data:{"replay_id":replay_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function ExamineReplayFailed(replay_id) {
    $.ajax({
        url:"",
        data:{"replay_id":replay_id},
        type:"post",
        success:function (){
            window.location.href="GetExamineBlog";
        }
    })
}
function choose(x) {
    switch (x){
        case 0:
            document.getElementById("blog").setAttribute("style","display:block")
            document.getElementById("comment").setAttribute("style","display:none")
            document.getElementById("replay").setAttribute("style","display:none")
            break
        case 1:
            document.getElementById("blog").setAttribute("style","display:none")
            document.getElementById("comment").setAttribute("style","display:block")
            document.getElementById("replay").setAttribute("style","display:none")
            break
        case 2:
            document.getElementById("blog").setAttribute("style","display:none")
            document.getElementById("comment").setAttribute("style","display:none")
            document.getElementById("replay").setAttribute("style","display:block")
            break
    }
}