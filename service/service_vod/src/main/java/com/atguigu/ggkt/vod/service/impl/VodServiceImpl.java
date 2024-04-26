package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.vod.service.VodService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VodServiceImpl implements VodService {
    private Logger logger;

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
            System.out.println(e.toString());
        }
        return null;
    }


    // 删除视频
    @Override
    public void removeVideo(String videoSourceId) {
        try {
            Credential credential = new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(credential, "");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(videoSourceId);
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
