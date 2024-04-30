package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.vod.service.VodService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VodServiceImpl implements VodService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //上传视频
    @Override
    public String uploadVideo() {
        try {
            VodUploadClient client = new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //上传请求对象
            VodUploadRequest request = new VodUploadRequest();
            //本地路径
            request.setMediaFilePath("D:\\Temp\\00_课程介绍.mp4");
            //指定任务流
            request.setProcedure("LongVideoPreset");
            //调用上传方法，传入接入点地域及上传请求。
            VodUploadResponse response = client.upload("ap-nanjing", request);
            //返回文件id保存到业务表，用于控制视频播放
            String fileId = response.getFileId();
            logger.info("Upload FileId =" + fileId);
            return fileId;
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return null;
    }

    // 删除视频
    @Override
    public void removeVideoById(Long videoSourceId) {

    }







}
