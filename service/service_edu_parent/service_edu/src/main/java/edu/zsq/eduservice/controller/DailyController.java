package edu.zsq.eduservice.controller;

import edu.zsq.eduservice.entity.vo.DailyVO;
import edu.zsq.eduservice.service.DailyService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduService/statistics")
public class DailyController {

    @Resource
    private DailyService dailyService;

    @PostMapping("produce/{day}")
    public JsonResult<Void> createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return JsonResult.OK;
    }


    @GetMapping("/show")
    public JsonResult<List<DailyVO>> showBySearch(@RequestParam(value ="begin" , required = false) String begin, @RequestParam(value = "end",required = false) String end){
        return JsonResult.success(dailyService.getDataBySearch(begin,end));
    }
}
