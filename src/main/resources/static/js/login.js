function checkAccount(){
    var account=document.getElementById("account")
    var password=document.getElementById("password")
    if (account.value===""||password.value===""){
        document.getElementById("msg").innerText="账号或密码为空"
    }
    else {
        $.ajax({
            url: "login_check",
            data: {"account":account.value,"password":password.value},
            type: "post",
            success:function (data){
                console.log(data)
                switch (data){
                    case "0":
                        document.getElementById("msg").innerText="用户名或密码错误"
                        break
                    case "1":
                        window.location.href="index2"
                        break
                }

            }
        })
    }
}
function checkEmail() {
    var email=document.getElementById("Email")
    var VerificationCode=document.getElementById("VerificationCode")
    if (email.value===""||VerificationCode.value===""){
        document.getElementById("msg").innerText="账号或密码为空"
    }
    else window.location.href="LoginByEmail?Email="+email.value+"&VerificationCode="+VerificationCode.value
}
function ChooseWay(x){
    switch (x){
        case 0:
            document.getElementById("ByAccount").setAttribute("style","display:none")
            document.getElementById("ByEmail").setAttribute("style","display:block")
            break;
        case 1:
            document.getElementById("ByAccount").setAttribute("style","display:block")
            document.getElementById("ByEmail").setAttribute("style","display:none")
            break;
    }
}
function getVerificationCode(){
    var email=document.getElementById("Email").value
    console.log(email)
    if (email=="")
        alert("请输入邮箱")
    else
        $.ajax({
            url:"/CreateVerificationCode",
            data:{
                "Email":email,
                "operation":"login"
            },
            async: false,
            type:"post",
            success:function (){
                alert("验证码已发送到你的邮箱，有效时间为5分钟")
            }
        })
}