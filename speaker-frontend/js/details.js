// 获取标签上的帖子id
var urlParams = new URLSearchParams(window.location.search);
var postId = urlParams.get("postId");

if (!postId) {
  toastr.warning("错误的帖子ID", "系统提示");
} else {
  // 根据帖子id查询帖子数据信息
  ajax(`/post/${postId}`, "GET", null, (data) => {
    var createTime = data.createTime.replace("T", " ");
    // html标签格式清除
    var content = data.content
      .replace(/&/g, "&amp;")
      .replace(/>/g, "&gt;")
      .replace(/</g, "&lt;")
      .replace(/\n/g, "<br>")
      .replace(/^( +)/gm, (match) => {
        return '&nbsp;'.repeat(match.length);
      })
    $("#title").text(data.title);
    $("#username").text(`发布者：${data.username}`);
    $("#createTime").text(`创建时间：${createTime}`);
    $("#content").html(content);
  });
}

$("#backButton").on("click", (event) => {
  event.preventDefault();

  history.back();
});
