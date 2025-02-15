package org.speaker.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.speaker.pojo.entity.Post;
import org.speaker.pojo.vo.PostVO;

import java.util.List;

@Mapper
public interface PostMapper {

    void insert(Post post);

    @Select("SELECT * FROM post WHERE id = #{id}")
    Post getById(Long id);

    @Select("SELECT * FROM post WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<PostVO> listByUserId(Long userId);

    @Select("SELECT * FROM post ORDER BY create_time DESC")
    Page<PostVO> pageQuery();

    @Delete("DELETE FROM post WHERE id = #{id}")
    void deleteById(Long id);
}
