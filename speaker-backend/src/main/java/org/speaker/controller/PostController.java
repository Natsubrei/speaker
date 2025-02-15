package org.speaker.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.speaker.result.PageResult;
import org.speaker.result.Result;
import org.speaker.constant.UserConstant;
import org.speaker.pojo.dto.PostDTO;
import org.speaker.pojo.entity.Post;
import org.speaker.pojo.entity.User;
import org.speaker.pojo.vo.PostVO;
import org.speaker.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    /**
     * 添加帖子
     * @param postDTO 帖子数据信息
     * @return 帖子id
     */
    @PostMapping("/add")
    public Result<Long> add(@RequestBody PostDTO postDTO) {
        if (postDTO == null) {
            return Result.error();
        }
        log.info("用户发帖：username={}", postDTO.getUsername());
        Long id = postService.addPost(postDTO);
        return Result.success(id);
    }

    /**
     * 根据帖子id获取帖子数据信息
     * @param id 帖子id
     * @return 帖子数据信息
     */
    @GetMapping("/{id}")
    public Result<Post> getDetail(@PathVariable Long id) {
        if (id == null) {
            return Result.error();
        }
        log.info("查询帖子内容：postId={}", id);
        Post post = postService.getDetailById(id);
        return Result.success(post);
    }

    /**
     * 分页查询
     * @param pageNum 页号
     * @param pageSize 每页的帖子数量
     * @return 帖子列表
     */
    @GetMapping("/list")
    public Result<PageResult> list(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return Result.error();
        }
        log.info("分页查询：pageNum={}, pageSize={}", pageNum, pageSize);
        PageResult pageResult = postService.pageQuery(pageNum, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据用户id查询帖子列表
     * @param userId 用户id
     * @return 用户所创建的帖子列表
     */
    @GetMapping("/list/{userId}")
    public Result<List<PostVO>> listByUserId(@PathVariable Long userId) {
        if (userId == null) {
            return Result.error();
        }
        log.info("查询用户帖子：userId={}", userId);
        List<PostVO> list = postService.listByUserId(userId);
        return Result.success(list);
    }

    /**
     * 根据帖子id删除帖子
     * @param id 帖子id
     * @param request http请求，包含当前用户的id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id, HttpServletRequest request) {
        if (id == null) {
            return Result.error();
        }
        User user = (User) request.getSession().getAttribute(UserConstant.USER_INFO);
        log.info("删除帖子：postId={}, userId={}", id, user.getId());
        postService.deleteById(id, user.getId());
        return Result.success();
    }
}
