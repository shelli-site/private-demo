package com.example.demo.web;

import com.example.demo.util.Log;
import com.example.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created By ShenXi
 * @Created On 2019/3/28 15:19
 * @Description :
 * @ClassName : TestController
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试API")
public class TestController {
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("redis"+"/set/{key}/{value}")
    @ApiOperation(value = "设置缓存")
    @Log(value = "请求了redis-set方法")
    public Result set(@PathVariable("key") String key, @PathVariable("value") String value) {
        //注意这里的 key不能为null spring 内部有检验
        redisTemplate.opsForValue().set(key, value);
        return Result.success(key + ":" + value);
    }

    @GetMapping("redis"+"/get/{key}")
    @ApiOperation(value = "根据key获取缓存")
    @Log(value = "请求了redis-get方法")
    public Result get(@PathVariable("key") String key) {

        return Result.success("key=" + key + ",value=" + redisTemplate.opsForValue().get(key));
    }

}
