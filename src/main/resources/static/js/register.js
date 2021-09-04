function getVerificationCode(){
    var email=document.getElementById("email").value
    console.log(email)
    if (email=="")
        alert("请输入邮箱")
    else
        $.ajax({
            url:"/CreateVerificationCode",
            data:{
                "Email":email,
                "operation":"register"
            },
            async: false,
            type:"post",
            success:function (){
                alert("验证码已发送到你的邮箱，有效时间为5分钟")
            }
            })
}