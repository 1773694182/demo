function goUSer(user_id) {

    $.ajax({
        type:"get",
        url:"/GetUserInfo",
        data:{
            "user_id":user_id
        },
        async: false,
        success:function () {
            window.location.href="/GetUserInfoByID?user_id="+user_id
        },
        error:function (){
            alert("error")
        }
    })
}


function show_label(){
    document.getElementById("get_label").setAttribute("style","display:block")
}
function show_classification() {
    document.getElementById("classification").setAttribute("style","display:block")
}
function hidden_classification() {
    document.getElementById("classification").setAttribute("style","display:none")
}
function hidden_label() {
    document.getElementById("get_label").setAttribute("style","display:none")

}