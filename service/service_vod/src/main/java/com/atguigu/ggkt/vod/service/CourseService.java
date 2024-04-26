package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPage(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseById(Long id);

    //修改课程信息
    void updateByCourse(CourseFormVo courseFormVo);

    CoursePublishVo getCoursePublishVo(Long id);

    boolean publishCourseById(Long id);

    void removeCourseById(Long id);
}
