package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Api(tags = "课程管理列表")
@RestController
@RequestMapping(value = "/admin/vod/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation("点播课程列表")
    @GetMapping("{page}/{limit}")
    public Result getCourse(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page, @ApiParam(name = "limit", value = "每页条数", required = true) @PathVariable Long limit, @ApiParam(name = "courseQueryVo", value = "查询对象", required = true) CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPage(coursePage, courseQueryVo);
        return Result.ok(map);
    }

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result saveCourse(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    //根据课程id获取课程信息
    @ApiOperation("获取课程信息")
    @GetMapping("get/{id}")
    public Result getCourse(@PathVariable("id") Long id) {
        CourseFormVo courseFormVo = courseService.getCourseById(id);
        return Result.ok(courseFormVo);
    }

    //修改课程信息
    @ApiOperation("修改课程信息")
    @PutMapping("update")
    public Result updateCourse(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateByCourse(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }


    //删除课程信息
    @ApiOperation("删除课程信息")
    @DeleteMapping("remove/{id}")
    public Result deleteCourse(@PathVariable Long id) {
        courseService.removeCourseById(id);
        return Result.ok();
    }

    //根据id获取课程发布信息
    @ApiOperation("根据id获取课程发布信息")
    @GetMapping("getCourseById/{id}")
    public Result getPublishVoById(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    //根据id发布课程
    @ApiOperation("根据id发布课程")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(@ApiParam(value = "课程ID", required = true)
                                    @PathVariable Long id) {
        boolean result = courseService.publishCourseById(id);
        return Result.ok(result);
    }
}

