package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.VodService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.atguigu.ggkt.vod.utils.Signature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
@CrossOrigin
public class VodController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VodService vodService;

    //返回客户端上传视频签名
    @ApiOperation("返回客户端上传视频签名")
    @GetMapping("sign")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天

        try {
            String signature = sign.getUploadSignature();
            logger.info("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            logger.error("获取签名失败");
            logger.info(e.toString());
            return Result.fail(null);
        }

    }

    //上传视频
    @ApiOperation("上传视频")
    @PostMapping("upload")
    public Result uploadVod() {
        String fileId = vodService.uploadVideo();
        return Result.ok(fileId);
    }

    //删除视频
    @DeleteMapping("remove/{videoSourceId}")
    public Result removeVideoById(@PathVariable Long videoSourceId) {
        vodService.removeVideoById(videoSourceId);
        return Result.ok();
    }

}
