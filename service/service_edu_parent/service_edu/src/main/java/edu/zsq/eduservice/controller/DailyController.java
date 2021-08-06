package edu.zsq.eduservice.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.zsq.eduservice.entity.vo.DailyVO;
import edu.zsq.eduservice.service.DailyService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/eduService/statistics/daily")
public class DailyController {

    @Resource
    private DailyService dailyService;

    @GetMapping("/produce")
    public JsonResult<Void> createStatisticsByDate(@RequestParam(value = "day")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                           LocalDate day) {
        dailyService.createStatisticsByDay(day);
        return JsonResult.OK;
    }


    @GetMapping("/show")
    public JsonResult<List<DailyVO>> showBySearch(@RequestParam(value = "begin", required = false) String begin, @RequestParam(value = "end", required = false) String end) {
        return JsonResult.success(dailyService.getDataBySearch(begin, end));
    }

    @GetMapping("/getStatisticsByDay")
    public JsonResult<DailyVO> getStatisticsByDay(@RequestParam(value = "day")
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                          LocalDate day) {
        return JsonResult.success(dailyService.getStatisticsByDay(day));
    }
}
