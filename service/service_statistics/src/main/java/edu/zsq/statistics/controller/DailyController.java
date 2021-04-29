package edu.zsq.statistics.controller;


import edu.zsq.statistics.service.DailyService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-27
 */
@RestController
@RequestMapping("/statisticsService/daily")
public class DailyController {

    @Resource
    private DailyService dailyService;

    @PostMapping("{day}")
    public JsonResult createStatisticsByDate(@PathVariable String day) {
        boolean statisticsByDay = dailyService.createStatisticsByDay(day);
        if (statisticsByDay){
            return JsonResult.success().message("添加统计数据成功");
        }else {
            return JsonResult.failure("添加统计数据成功失败");
        }
    }


    @GetMapping("/showBySearch")
    public JsonResult showBySearch(@RequestParam(value ="begin" , required = false) String begin, @RequestParam(value = "end",required = false) String end){
        Map<String, Object> map = dailyService.getDataBySearch(begin,end);
        return JsonResult.success().data(map).message("查询成功");
    }

}

