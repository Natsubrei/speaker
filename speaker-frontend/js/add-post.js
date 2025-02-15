$("#submitButton").on("click", (event) => {
  event.preventDefault();

  // 标题长度不得大于64个字符
  if ($("#title").val().length > 64) {
    toastr.warning("标题过长", "系统提示");
    return;
  }
  // 正文长度不超过65535个字节
  if (byteLength($("#content").val()) > 65535) {
    toastr.warning("正文内容过长", "系统提示");
    return;
  }

  var data = {
    userId: window.userId,
    username: window.username,
    title: $("#title").val(),
    content: $("#content").val()
  };

  ajax("/post/add", "POST", data, () => {
    toastr.success("发布成功, 即将返回", "系统提示", {
      timeOut: "1000",
    });
    setTimeout(() => {
      window.location.pathname = "/pages/user.html";
    }, 1000);
  });
});

// 计算字符串所占字节数
function byteLength(str) {
  var len = 0;
  for (var i = 0; i < str.length; i++) {
    var code = str.charCodeAt(i);
    if (code <= 0x7f) {
      len += 1;  // 单字节字符
    } else if (code <= 0x7ff) {
      len += 2;  // 双字节字符
    } else if (code >= 0xd800 && code <= 0xdbff) {
      // 高代理
      if (i + 1 < str.length) {
        var nextCode = str.charCodeAt(i + 1);
        if (nextCode >= 0xdc00 && nextCode <= 0xdfff) {
          len += 4;  // 代理对（四字节字符）
          i++;  // 跳过低代理
        } else {
          len += 3;  // 三字节字符
        }
      } else {
        len += 3;  // 三字节字符
      }
    } else {
      len += 3;  // 三字节字符
    }
  }
  return len;
}
