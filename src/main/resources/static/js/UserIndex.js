function choose(chose) {
    if(chose==1){
        document.getElementById("post_blog").setAttribute("style","display:block")
        document.getElementById("collection_blog").setAttribute("style","display:none")
        document.getElementById("follow_user").setAttribute("style","display:none")
    }
    else if(chose==2){
        document.getElementById("collection_blog").setAttribute("style","display:block")
        document.getElementById("post_blog").setAttribute("style","display:none")
        document.getElementById("follow_user").setAttribute("style","display:none")

    }
    else if(chose==3){
        document.getElementById("collection_blog").setAttribute("style","display:none")
        document.getElementById("post_blog").setAttribute("style","display:none")
        document.getElementById("follow_user").setAttribute("style","display:block")

    }
}
function CancelFollow(FriendID){
    $.ajax({
        url:"/CancelFollow",
        type:"post",
        data:{"FriendID":FriendID},
        success:function () {
            document.getElementById("FollowButton"+FriendID).setAttribute("style","display:block")
            document.getElementById("CancelButton"+FriendID).setAttribute("style","display:none")
        }
    })
}
function Follow(FriendID) {
    $.ajax({
        url:"/AddFriend",
        type:"post",
        data:{"FriendID":FriendID},
        success:function () {
            document.getElementById("FollowButton"+FriendID).setAttribute("style","display:none")
            document.getElementById("CancelButton"+FriendID).setAttribute("style","display:block")
        }
    })
}
function CancelCollection(blog_id){
    $.ajax({
        url:"/CollectionBlog",
        type:"post",
        data:{"blog_id":blog_id},
        success:function () {
            document.getElementById("Collection"+blog_id).setAttribute("style","display:block")
            document.getElementById("CancelCollection"+blog_id).setAttribute("style","display:none")
        }
    })
}
function Collection(blog_id) {
    $.ajax({
        url:"/CollectionBlog",
        type:"post",
        data:{"blog_id":blog_id},
        success:function () {
            document.getElementById("Collection"+blog_id).setAttribute("style","display:none")
            document.getElementById("CancelCollection"+blog_id).setAttribute("style","display:block")
        }
    })
}
function DeleteBlog(blog_id) {
    $.ajax({
        url:"/DeleteBlog",
        data:{"blog_id":blog_id},
        type:"post",
        success:function () {

        }
    })
}
function HideBlog(blog_id) {
    $.ajax({
        url:"/HideBlog",
        data:{"blog_id":blog_id},
        type:"post",
        success:function () {
            console.log(blog_id)
            document.getElementById("switch"+blog_id).setAttribute("style","display:none")
            document.getElementById("HideBlog"+blog_id).setAttribute("style","display:none")
            document.getElementById("CancelHide"+blog_id).setAttribute("style","display:block")
        }
    })
}
function CancelHide(blog_id) {
    $.ajax({
        url:"/CancelHide",
        data:{"blog_id":blog_id},
        type:"post",
        success:function () {
            console.log(blog_id)
            document.getElementById("switch"+blog_id).setAttribute("style","display:none")
            document.getElementById("HideBlog"+blog_id).setAttribute("style","display:block")
            document.getElementById("CancelHide"+blog_id).setAttribute("style","display:none")
        }
    })
}