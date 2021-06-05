package edu.zsq.cms.controller;

import cn.hutool.core.util.RandomUtil;
import edu.zsq.cms.service.MsmService;
import edu.zsq.cms.util.MsmRandomUtil;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduCms/msm")
public class MsmController {

    /**
     * StringRedisTemplate extends RedisTemplate<String, String>
     */

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MsmService msmService;


    @GetMapping("/send/{mobile}")
    @ApiOperation(value = "根据手机号发送短信")
    public JsonResult<Void> send(@PathVariable String mobile) {

//      生成随机数,并传递给阿里云进行发送
        String code = MsmRandomUtil.getFourBitRandom();

        Map<String, String> params = new HashMap<>(1);
        params.put("code", code);

        if (msmService.send(params, mobile)) {
//            放入redis并设置5分钟
            redisTemplate.opsForValue().set(mobile, code, 5, TimeUnit.MINUTES);
            return JsonResult.OK;
        } else {
            return JsonResult.failure("验证码发送失败");
        }
    }

}
