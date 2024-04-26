package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户登陆接口")
@RestController
@RequestMapping("/admin/vod/user")
@CrossOrigin(origins = "*")
public class UserController {

    //login
    @PostMapping("login")
    public Result login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin");
        return Result.ok(map);
    }

    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }

    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

}
