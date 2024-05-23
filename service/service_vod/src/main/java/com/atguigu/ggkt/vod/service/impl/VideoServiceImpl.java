package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Autowired
    private VodService vodService;

    // 根据课程id删除课程小节 同时删除小节里的视频
    @Override
    public void removeByVideoId(Long id) {
        //id查询小节
        Video video = baseMapper.selectById(id);
        //获取video里的视频id
        String videoSourceId = video.getVideoSourceId();
        // 判断视频id是否为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }

    //根据课程id删除课程小节 同时删除所有小节视频
    @Override
    public void removeVideoByCourseId(Long id) {

        //根据课程id查询课程所有小节
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId, id);
        List<Video> videoList = baseMapper.selectList(wrapper);

        //遍历所有小节集合得到每个小节，获取每个小节的视频id
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            // 判断视频id是否为空 不为空删除腾讯云视频
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        }
        //根据id删除小节
        baseMapper.deleteById(id);
    }


}
