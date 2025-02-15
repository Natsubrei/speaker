package org.speaker.service;

import org.speaker.pojo.dto.PostDTO;
import org.speaker.pojo.entity.Post;
import org.speaker.pojo.vo.PostVO;
import org.speaker.result.PageResult;

import java.util.List;

public interface PostService {

    /**
     * 添加帖子
     * @param postDTO 帖子数据信息
     * @return 创建成功则返回帖子id
     */
    Long addPost(PostDTO postDTO);

    /**
     * 根据帖子id删除帖子
     * @param id 帖子id
     * @param userId 用户id
     */
    void deleteById(Long id, Long userId);

    /**
     * 根据帖子id获取帖子数据信息
     * @param id 帖子id
     * @return 帖子数据信息
     */
    Post getDetailById(Long id);

    /**
     * 根据用户id获取帖子列表
     * @param userId 用户id
     * @return 用户所创建的帖子列表
     */
    List<PostVO> listByUserId(Long userId);

    /**
     * 分页查询
     * @param pageNum 页号
     * @param pageSize 每页的帖子数量
     * @return 查询结果
     */
    PageResult pageQuery(Integer pageNum, Integer pageSize);
}
