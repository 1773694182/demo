var user_id=document.cookie;
var cookie=user_id.split("; ")
console.log(user_id)
var account=""
var pwd=""
var state=""
for(var i=0;i<cookie.length;i++){
    var arr=cookie[i].split("=")
    if(arr[0]=="account")
        account=arr[1]
    else if(arr[0]=="pwd")
        pwd=arr[1]
    else if(arr[0]=="state")
        state=arr[1]
}
// if (state!="yes")
console.log(account)
console.log(pwd)
console.log(state)
    $.ajax({
        url:"/loginByShiro",
        type:"post",
        data:{"account":account,"password":pwd,"op":"auto"},
        success:function (data) {
            switch (data){
                case "0":
                    document.getElementById("msg").innerText="用户名或密码错误"
                    break
                case "1":
                    // window.location.href="index2"
                    break
            }
        }
    })