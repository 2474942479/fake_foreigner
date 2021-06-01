package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.dto.BannerDTO;
import edu.zsq.eduservice.entity.dto.query.BannerQueryDTO;
import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.eduservice.service.EduBannerService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/eduService/banner")
public class EduBannerController {

    @Resource
    private EduBannerService bannerService;


    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/getBannerList")
    public JsonResult<PageData<BannerVO>> getBannerList(@RequestBody BannerQueryDTO bannerQueryDTO) {
        return JsonResult.success(bannerService.getBannerList(bannerQueryDTO));
    }

    @ApiOperation(value = "根据id获取Banner信息")
    @GetMapping("/getBannerById/{id}")
    public JsonResult<BannerVO> getBannerById(@PathVariable String id) {
        return JsonResult.success(bannerService.getBannerById(id));
    }


    @ApiOperation(value = "新增Banner")
    @PostMapping("/saveOrUpdateBanner")
    public JsonResult<Void> saveOrUpdateBanner(@RequestBody BannerDTO bannerDTO) {
        bannerService.saveOrUpdateBanner(bannerDTO);
        return JsonResult.OK;
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/removeBanner/{id}")
    public JsonResult<Void> remove(@PathVariable String id) {
        bannerService.removeBanner(id);
        return JsonResult.OK;
    }

}

