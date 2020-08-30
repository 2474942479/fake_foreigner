package edu.zsq.statistics.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 张
 */
@Component
@FeignClient(name = "service8006-user",fallback = UserServerImpl.class)
public interface UserServer {

    /**
     * 每天注册人数
     * @param day
     * @return
     */
    @GetMapping("/user/getRegisterNumber/{day}")
    Integer getRegisterNumber(@PathVariable("day") String day);
}
