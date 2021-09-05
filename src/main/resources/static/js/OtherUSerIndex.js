function addFriend(user_id) {
    $.ajax({
        url:"/AddFriend",
        data:{"FriendID":user_id},
        success:function (){
            alert("关注成功")
        }
    })
}
function chat(user_id) {
    
}