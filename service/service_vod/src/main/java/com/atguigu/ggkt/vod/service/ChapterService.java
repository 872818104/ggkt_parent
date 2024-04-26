package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterTreeList(Long courseId);

    void removeChapterByCourseId(Long id);
}
