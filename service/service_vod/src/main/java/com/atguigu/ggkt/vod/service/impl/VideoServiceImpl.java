package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    //根据课程id删除课程小节
    @Override
    public void removeVideoByCourseId(Long id) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,id);
        baseMapper.delete(wrapper);
    }
}
