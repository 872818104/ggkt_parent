package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final TeacherService teacherService;

    private final SubjectService subjectService;

    private final CourseDescriptionService descriptionService;

    private final VideoService videoService;

    private final ChapterService chapterService;

    public CourseServiceImpl(ChapterService chapterService, TeacherService teacherService, SubjectService subjectService, CourseDescriptionService descriptionService, VideoService videoService) {
        this.chapterService = chapterService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.descriptionService = descriptionService;
        this.videoService = videoService;
    }

    @Override
    public Map<String, Object> findPage(Page<Course> coursePage, CourseQueryVo courseQueryVo) {

        // 获取条件值
        String title = courseQueryVo.getTitle();
        Long teacherId = courseQueryVo.getTeacherId();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();

        // 判断条件为空
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like(Course::getTitle, title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(Course::getSubjectId, subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(Course::getSubjectParentId, subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(Course::getTeacherId, teacherId);
        }
        // 调用方法查询
        Page<Course> pages = baseMapper.selectPage(coursePage, wrapper);
        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数

        // 每页数据集合
        List<Course> records = pages.getRecords();

        // 遍历封装讲师和分类名称
        records.forEach(this::getTeacherOrSubjectName);

        // 封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }


    // 添加课程基本信息
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {

        //保存课程基本信息 操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        // 保存课程描述信息
        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
        description.setCourseId(courseFormVo.getId());
        descriptionService.save(description);

        // 返回课程id
        return course.getId();
    }


    // 获取讲师和分类名称
    public void getTeacherOrSubjectName(Course course) {

        // 根据讲师id获取讲师姓名
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (!StringUtils.isEmpty(teacher)) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }

        // 查询分类名称
        // 1.一级分类
        Subject subject1 = subjectService.getById(course.getSubjectId());
        if (!StringUtils.isEmpty(subject1)) {
            String title = subject1.getTitle();
            course.getParam().put("subjectTitle", title);
        }

        // 2.二级分类
        Subject subject2 = subjectService.getById(course.getSubjectParentId());
        if (!StringUtils.isEmpty(subject2)) {
            String title = subject2.getTitle();
            course.getParam().put("subjectParentTitle", title);
        }
    }


    //根据课程id获取课程信息
    @Override
    public CourseFormVo getCourseById(Long id) {
        //获取课程基本信息
        log.info("获取课程基本信息");
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        //课程描述信息
        log.info("课程描述信息");
        CourseDescription courseDescription = descriptionService.getById(id);
        //封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    //修改课程信息
    @Override
    public void updateByCourse(CourseFormVo courseFormVo) {
        //修改课程基本信息
        log.info("修改课程基本信息");
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        //修改课程描述信息
        log.info("修改课程描述信息");
        CourseDescription courseDescription = descriptionService.getById(course.getId());
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        descriptionService.updateById(courseDescription);
    }

    //根据id获取课程发布信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    //根据id发布课程
    @Override
    public boolean publishCourseById(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        return this.updateById(course);
    }

    //根据课程id删除课程
    @Override
    public void removeCourseById(Long id) {
        //根据课程id删除课程小节
        videoService.removeVideoByCourseId(id);
        //根据课程id删除课程章节
        chapterService.removeChapterByCourseId(id);
        //删除课程描述
        descriptionService.removeById(id);
        //删除课程信息
        baseMapper.deleteById(id);
    }
}
