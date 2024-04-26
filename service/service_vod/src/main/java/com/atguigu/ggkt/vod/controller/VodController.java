package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频")
    @PostMapping("upload")
    public Result uploadVod() {
        String fileId = vodService.uploadVideo();
        return Result.ok(fileId);
    }


    //删除视频
    @DeleteMapping("remove/{videoSourceId}")
    public Result removeVideo(@PathVariable String videoSourceId) {
        vodService.removeVideo(videoSourceId);
        return Result.ok();
    }

}
