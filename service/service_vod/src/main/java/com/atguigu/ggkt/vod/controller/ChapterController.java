package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vod.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Api(tags = "课程章节列表")
@RestController
@RequestMapping(value = "/admin/vod/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //1.获取章节小结列表
    @ApiOperation("获取章节小结列表")
    @GetMapping("getChapterTreeList/{courseId}")
    public Result getChapter(@PathVariable Long courseId) {
        List<ChapterVo> chapterList = chapterService.getChapterTreeList(courseId);
        if (chapterList == null) {
            return Result.ok(null);
        } else {
            return Result.ok(chapterList);
        }
    }

    //2.添加章节
    @ApiOperation("添加章节")
    @PostMapping("save")
    public Result saveChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok(null);
    }

    //3.修改-根据id查询
    @ApiOperation("获取章节")
    @GetMapping("get/{id}")
    public Result saveChapter(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    //4.修改-最终实现
    @PutMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

    //5.删除章节
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok(null);
    }
}