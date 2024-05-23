package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-03-29
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterTreeList(Long courseId) {
        // 最终数据
        List<ChapterVo> chapterVoList = new ArrayList<>();
        // 根据课程id获取课程里的所有章节
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getCourseId, courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapper);

        LambdaQueryWrapper<Video> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(wrapper1);

        //封装章节
        //遍历所有章节
        for (Chapter chapter : chapterList) {
            // 得到每个课程章节
            // chapter  ==>  chapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);

            // 根据课程id获取课程里的所有小节
            // 创建list集合
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList) {
                // 得到每个课程小节
                //章节id 与 chapter_id
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            // 把章节里所有的小节集合放到每个章节里
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }


    //根据课程id删除课程章节
    @Override
    public void removeChapterByCourseId(Long id) {
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getCourseId, id);
        baseMapper.delete(wrapper);
    }
}
