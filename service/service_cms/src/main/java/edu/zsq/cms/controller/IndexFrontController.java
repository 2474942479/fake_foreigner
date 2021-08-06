package edu.zsq.cms.controller;

import edu.zsq.cms.entity.vo.IndexFrontVO;
import edu.zsq.cms.service.IndexFrontService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前台首页
 *
 * @author 张
 */
@RestController
@RequestMapping("/eduCms/indexFront")
public class IndexFrontController {

    @Resource
    private IndexFrontService indexFrontService;

    @ApiOperation(value = "查询前两条幻灯片banner记录 前8条热门课程 前4个名师")
    @GetMapping("/getIndexInfo")
    public JsonResult<IndexFrontVO> getIndexInfo() {
        return JsonResult.success(indexFrontService.getIndexInfo());
    }

}
