package com.atguigu.ggkt.vod.mapper;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(Long id);
}
