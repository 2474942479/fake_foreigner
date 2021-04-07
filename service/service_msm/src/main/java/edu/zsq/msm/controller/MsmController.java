package edu.zsq.msm.controller;

import edu.zsq.msm.service.MsmService;
import edu.zsq.msm.utils.RandomUtil;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 张
 * 发送短信服务
 */
@RestController
@RequestMapping("/eduMsm/msm")
public class MsmController {

    /**
     * StringRedisTemplate extends RedisTemplate<String, String>
     */

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MsmService msmService;


    /**
     * 根据手机号发送短信
     *
     * @param mobile
     * @return
     */
    @GetMapping("/send/{mobile}")
    public JsonResult<Void> send(@PathVariable String mobile) {

//      生成随机数,并传递给阿里云进行发送
        String code = RandomUtil.getFourBitRandom();

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
