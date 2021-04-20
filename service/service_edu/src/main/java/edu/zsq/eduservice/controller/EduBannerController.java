package edu.zsq.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.eduservice.entity.EduBanner;
import edu.zsq.eduservice.entity.dto.BannerDTO;
import edu.zsq.eduservice.entity.dto.query.BannerQueryDTO;
import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.eduservice.service.EduBannerService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/pageBanner")
    public JsonResult<PageData<BannerVO>> pageBanner(@RequestBody BannerQueryDTO bannerQueryDTO) {
        return JsonResult.success(bannerService.pageBanner(bannerQueryDTO));
    }

    @ApiOperation(value = "根据id获取Banner信息")
    @GetMapping("/getBanner/{id}")
    public JsonResult<BannerVO> getBanner(@PathVariable String id) {
        return JsonResult.success(bannerService.getBanner(id));
    }


    @ApiOperation(value = "新增Banner")
    @PostMapping("/saveBanner")
    public JsonResult<Void> saveBanner(@RequestBody BannerDTO bannerDTO) {
        return bannerService.saveBanner(bannerDTO);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/updateBanner")
    public JsonResult<Void> updateBanner(@RequestBody BannerDTO bannerDTO) {
        return bannerService.updateBanner(bannerDTO);
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/removeBanner/{id}")
    public JsonResult<Void> remove(@PathVariable String id) {
        return bannerService.removeBanner(id);
    }

}

