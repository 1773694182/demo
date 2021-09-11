var user_id=document.cookie;
var cookie=user_id.split("; ")
console.log(user_id)
var account=""
var pwd=""
for(var i=0;i<cookie.length;i++){
    var arr=cookie[i].split("=")
    if(arr[0]=="account")
        account=arr[1]
    else if(arr[0]=="pwd")
        pwd=arr[1]
}
$.ajax({
    url:"/AutomaticLogon",
    type:"post",
    data:{"account":account,"pwd":pwd},
    success:function (data) {
        // switch (data){
        //     case "0":
        //         alert("登录失败")
        //         break
        //     case "1":
        //         alert("登录成功")
        //         break
        // }
    }
})