var pageSize = 10; // 每页加载的帖子数量
var currentPage = 1;   // 当前页码
var totalPosts = 0;    // 总帖子数

// 初始化页面
fetchPosts(currentPage);

// 滚动监听，用于实现无限滚动
$(window).on("scroll", (event) => {
  event.preventDefault();

  var windowTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var scrollHeight = $(document).height();

  if (windowTop + windowHeight + 100 >= scrollHeight
      && currentPage * pageSize < totalPosts) {
    currentPage++;
    fetchPosts(currentPage);
  }
});

// 获取初始数据
function fetchPosts(pageNum) {
  ajax(`/post/list?pageNum=${pageNum}&pageSize=${pageSize}`, "GET", null, (data) => {
    if (!data) return;

    totalPosts = data.total;
    data.records.forEach((post) => {
      var date = new Date(post.createTime);
      // 提取年、月、日
      var year = (date.getFullYear() % 100).toString().padStart(2, "0");
      var month = (date.getMonth() + 1).toString().padStart(2, "0");
      var day = date.getDate().toString().padStart(2, "0");
      // 格式化日期时间
      var formattedDateTime = `${year}-${month}-${day}`;

      var listItem = `
        <a href="/pages/details.html?postId=${post.id}" class="list-group-item list-group-item-action">
          <div class="d-flex w-100 justify-content-between">
            <span class="fs-5 title-brief">${escape(post.title)}</span>
            <span class="text-body-secondary create-time">${formattedDateTime}</span>
          </div>
          <p class="my-1 fw-light content-brief">${escape(post.contentBrief)}</p>
          <small class="text-body-secondary">${escape(post.username)}</small>
        </a>
      `;
      // 添加新的帖子项
      $(".list-group").append(listItem);
    });
  });
}

// html格式清除
function escape(str) {
  return str
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}
