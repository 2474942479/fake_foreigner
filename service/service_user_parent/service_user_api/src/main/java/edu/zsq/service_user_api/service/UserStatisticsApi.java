package edu.zsq.service_user_api.service;

import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

/**
 * @author 张
 */
@FeignClient(name = "service8150-user", path = "/")
@Api(value = "用户服务", tags = {"用户统计服务"})
@Component
public interface UserStatisticsApi {

    @GetMapping("/getRegisterNumber/{day}")
    @ApiOperation(value = "统计注册人数")
    @ApiImplicitParam(name = "day", value = "当前日期", required = true)
    JsonResult<Integer> getRegisterNumber(@PathVariable LocalDate day);
}
