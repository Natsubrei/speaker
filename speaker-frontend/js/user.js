$("#username").text(`用户名：${window.username}`);

// 查询用户帖子
ajax(`/post/list/${window.userId}`, "GET", null, (data) => {
  list(data);
});

$("#logOutButton").on("click", (event) => {
  event.preventDefault();

  $.confirm({
    title: "退出登录",
    content: "您确定要退出登录吗？",
    buttons: {
      ok: {
        text: "确定",
        btnClass: "btn-primary",
        action: () => {
          ajax("/user/logout", "POST", null, (_data) => {
            window.location.pathname = "/pages/sign-in.html";
          });
        }
      },
      cancel: {
        text: "取消"
      }
    }
  });
});

function list(data) {
  // 清空列表
  $("#postList").empty();

  // 遍历返回的数据并创建新的帖子项
  data.forEach((post) => {
    var listItem = `
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <a href="/pages/details.html?postId=${post.id}" class="text-decoration-none">${post.title}</a>
        <button class="btn btn-danger btn-sm delete-button" data-post-id="${post.id}">删除</button>
      </li>
    `;
    // 将新创建的帖子项添加到列表中
    $("#postList").append(listItem);
  });

  $(".delete-button").on("click", (event) => {
    event.preventDefault();

    var target = $(event.currentTarget);
    var postId = target.data("post-id");

    $.confirm({
      title: "删除帖子",
      content: "您确定要删除这个帖子吗？",
      buttons: {
        ok: {
          text: "确定",
          btnClass: "btn-primary",
          action: () => {
            ajax(`/post/delete/${postId}`, "DELETE", null, (_data) => {
              target.closest("li").remove();
              toastr.success("删除成功", "系统消息");
            });
          }
        },
        cancel: {
          text: "取消"
        }
      }
    });
  });
}
