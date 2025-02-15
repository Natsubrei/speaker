$("#signInButton").on("click", (event) => {
  event.preventDefault();

  var data = {
    username: $("#floatingInput").val(),
    password: $("#floatingPassword").val()
  };

  // 登录并跳转
  ajax("/user/login", "POST", data, (_data) => {
    window.location.pathname = "/pages/square.html";
  });
});
