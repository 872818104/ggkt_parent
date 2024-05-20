package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
public interface VideoService extends IService<Video> {


    void removeVideoByCourseId(Long id);

    void removeByVideoId(Long id);
}
