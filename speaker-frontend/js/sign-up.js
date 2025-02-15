$("#signUpButton").on("click", (event) => {
  event.preventDefault();

  var data = {
    username: $("#floatingInput").val(),
    password: $("#floatingPassword").val(),
    pwdCheck: $("#floatingPasswordCheck").val()
  };

  // 注册并跳转
  ajax("/user/register", "POST", data, (_data) => {
    toastr.success("注册成功, 即将跳转登录页", "系统提示", {
      timeOut: "1000",
    });
    setTimeout(() => {
      window.location.pathname = "/pages/sign-in.html";
    }, 1000);
  });
});

// 用户名格式提示
$("#floatingInput").on("blur", (event) => {
  event.preventDefault();

  var pattern = /^\w{4,16}$/;
  var username = $("#floatingInput").val();

  if (username != "" && !pattern.test(username)) {
    toastr.warning("4~16位数字、字母和下划线组合", "用户名格式错误");
  }
});

// 密码格式提示
$("#floatingPassword").on("blur", (event) => {
  event.preventDefault();

  var pattern = /^\w{6,32}$/;
  var password = $("#floatingPassword").val();

  if (password != "" && !pattern.test(password)) {
    toastr.warning("6~32位数字、字母和下划线组合", "密码格式错误");
  }
});

// 确认密码格式提示
$("#floatingPasswordCheck").on("blur", (event) => {
  event.preventDefault();

  var pattern = /^\w{6,32}$/;
  var pwdCheck = $("#floatingPasswordCheck").val();

  if (pwdCheck != "" && !pattern.test(pwdCheck)) {
    toastr.warning("6~32位数字、字母和下划线组合", "确认密码格式错误");
  }
});
