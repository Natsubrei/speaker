package org.speaker.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.speaker.constant.MessageConstant;
import org.speaker.exception.PostException;
import org.speaker.mapper.PostMapper;
import org.speaker.mapper.UserMapper;
import org.speaker.pojo.dto.PostDTO;
import org.speaker.pojo.entity.Post;
import org.speaker.pojo.entity.User;
import org.speaker.pojo.vo.PostVO;
import org.speaker.result.PageResult;
import org.speaker.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 添加帖子
     * @param postDTO 帖子数据信息
     * @return 创建成功则返回帖子id
     */
    @Override
    public Long addPost(PostDTO postDTO) {
        String title = postDTO.getTitle();
        String content = postDTO.getContent();
        // 标题和正文均不能为空
        if (StringUtils.isAnyBlank(title, content)) {
            throw new PostException(MessageConstant.TITLE_OR_CONTENT_NOT_INPUT);
        }
        // 标题长度不得大于64个字符
        if (title.length() > 64) {
            throw new PostException(MessageConstant.TITLE_SO_LONG);
        }
        // 正文长度不超过65535个字节
        if (content.getBytes(StandardCharsets.UTF_8).length > 65535) {
            throw new PostException(MessageConstant.CONTENT_SO_LONG);
        }
        // 发帖人的id应与用户名对应
        User user = userMapper.getById(postDTO.getUserId());
        if (!user.getUsername().equals(postDTO.getUsername())) {
            throw new PostException(MessageConstant.ILLEGAL_OPERATION);
        }
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        // 截取正文前面一部分作为预览
        String contentBrief = postDTO.getContent().substring(0, Math.min(256, content.length()));
        post.setContentBrief(contentBrief);
        // 添加帖子
        postMapper.insert(post);
        return post.getId();
    }

    /**
     * 根据帖子id删除帖子
     * @param id 帖子id
     * @param userId 用户id
     */
    @Override
    public void deleteById(Long id, Long userId) {
        Post post = postMapper.getById(id);
        // 要删除的帖子应存在
        if (post == null) {
            throw new PostException(MessageConstant.POSTER_NOT_FOUND);
        }
        // 只能删除自己发布的帖子
        if (!userId.equals(post.getUserId())) {
            throw new PostException(MessageConstant.ILLEGAL_OPERATION);
        }
        postMapper.deleteById(id);
    }

    /**
     * 根据帖子id获取帖子数据信息
     * @param id 帖子id
     * @return 帖子数据信息
     */
    @Override
    public Post getDetailById(Long id) {
        Post post = postMapper.getById(id);
        // 若帖子不存在，则抛出异常
        if (post == null) {
            throw new PostException(MessageConstant.POSTER_NOT_FOUND);
        }
        return post;
    }

    /**
     * 根据用户id获取帖子列表
     * @param userId 用户id
     * @return 用户所创建的帖子列表
     */
    @Override
    public List<PostVO> listByUserId(Long userId) {
        User user = userMapper.getById(userId);
        // 若用户不存在，则抛出异常
        if (user == null) {
            throw new PostException(MessageConstant.ILLEGAL_OPERATION);
        }
        return postMapper.listByUserId(userId);
    }

    /**
     * 分页查询
     * @param pageNum 页号
     * @param pageSize 每页的帖子数量
     * @return 查询结果
     */
    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize) {
        // 使用PageHelper进行分页查询
        PageHelper.startPage(pageNum, pageSize);
        Page<PostVO> page = postMapper.pageQuery();
        // 查询失败，则抛出异常
        if (page == null) {
            throw new PostException(MessageConstant.PAGE_QUERY_ERROR);
        }
        // 返回帖子总数以及此次查询的结果
        return new PageResult(page.getTotal(), page);
    }
}
