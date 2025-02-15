// 配置消息提示框参数
toastr.options = {
  closeButton: true,
  debug: false,
  progressBar: true,
  positionClass: "toast-top-center",
  onclick: null,
  showDuration: "300",
  hideDuration: "1000",
  timeOut: "2000",
  extendedTimeOut: "1000",
  showEasing: "swing",
  hideEasing: "linear",
  showMethod: "fadeIn",
  hideMethod: "fadeOut"
};

// http请求的前缀，根据需要设置请求端口
var base = `http://${window.location.hostname}:8080/api`;

// 同步请求，查询当前用户信息
ajax("/user/current", "GET", null, (data) => {
  var pathname = window.location.pathname;
  if (data) {
    window.userId = data.id;
    window.username = data.username;
    if (pathname == "/pages/sign-in.html" || pathname == "/") {
      // 若已登录，且处于登录页，则直接跳转到广场
      window.location.pathname = "/pages/square.html";
    }
  } else if (pathname != "/pages/sign-in.html" 
          && pathname != "/pages/sign-up.html") {
    // 若未登录，且不处于登录页或注册页，则直接跳转到登录页
    window.location.pathname = "/pages/sign-in.html";
  }
}, false);

// 对JQuery提供的ajax函数进一步封装，增加消息提示功能
function ajax(path, type, data, action, isAsync = true) {
  $.ajax({
    url: base + path,
    type: type,
    contentType: "application/json",
    dataType: "json",
    async: isAsync,
    xhrFields: {
      withCredentials: true
    },
    data: JSON.stringify(data),
    success: (res) => {
      if (res.code == 1) {
        // http请求成功，则调用action函数
        action(res.data);
      } else if (res.code == 0) {
        toastr.warning(res.msg, "系统提示");
      }
    },
    error: (_xhr, textStatus) => {
      toastr.error(textStatus, "系统错误");
    }
  });
}
