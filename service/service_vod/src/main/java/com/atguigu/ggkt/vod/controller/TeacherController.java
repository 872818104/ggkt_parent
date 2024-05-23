package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cc
 * @since 2024-03-20
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public Result getList() {
        List<Teacher> teacherList = teacherService.list();
        return Result.ok(teacherList);
    }

    //删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public boolean removeById(@ApiParam(name = "id", value = "ID", required = true) @PathVariable Long id) {
        return teacherService.removeById(id);
    }

    //条件查询分页列表
    @ApiOperation("获取分页列表")
    @PostMapping("findQueryPage/{page}/{limit}")
    public Result findQueryPage(@PathVariable Long page,
                                @PathVariable Long limit,
                                TeacherQueryVo teacherQueryVo) {
        if (teacherQueryVo == null) {
            List<Teacher> teacherList = teacherService.list();
            return Result.ok(teacherList);
        } else {
            //创建page对象，传递当前页和每页记录数
            Page<Teacher> pageParam = new Page<>(page, limit);
            //获取条件值
            String name = teacherQueryVo.getName();//讲师名称
            Integer level = teacherQueryVo.getLevel();//讲师级别
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();//开始时间
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();//结束时间
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like(Teacher::getName, name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq(Teacher::getLevel, level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge(Teacher::getJoinDate, joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le(Teacher::getJoinDate, joinDateEnd);
            }
            IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //4.添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok();
    }

    //5.获取讲师
    @ApiOperation("获取讲师")
    @GetMapping("get/{id}")
    public Result getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    //6.修改讲师
    @ApiOperation("修改讲师")
    @PutMapping("update")
    public Result updateByTeacher(@RequestBody Teacher teacher) {
        boolean success = teacherService.updateById(teacher);
        if (success) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("批量删除讲师")
    @PostMapping("batchRemove")
    public Result removeByIds(@RequestBody List<Long> idList) {
        teacherService.removeByIds(idList);
        return Result.ok(null);
    }
}


