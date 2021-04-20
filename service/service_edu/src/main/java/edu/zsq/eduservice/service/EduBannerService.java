package edu.zsq.eduservice.service;

import edu.zsq.eduservice.entity.EduBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.dto.BannerDTO;
import edu.zsq.eduservice.entity.dto.query.BannerQueryDTO;
import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-24
 */
public interface EduBannerService extends IService<EduBanner> {

    /**
     * 分页查询
     *
     * @param bannerQueryDTO 查询条件
     * @return 查询结果
     */
    PageData<BannerVO> pageBanner(BannerQueryDTO bannerQueryDTO);

    /**
     * 获取Banner详细信息
     *
     * @param id banner Id
     * @return 详细信息
     */
    BannerVO getBanner(String id);

    /**
     * 添加Banner
     *
     * @param bannerDTO bannerDTO
     * @return 添加结果
     */
    JsonResult<Void> saveBanner(BannerDTO bannerDTO);

    /**
     * 修改Banner
     *
     * @param bannerDTO bannerDTO
     * @return 修改结果
     */
    JsonResult<Void> updateBanner(BannerDTO bannerDTO);

    /**
     * 删除Banner
     *
     * @param id id
     * @return 删除结果
     */
    JsonResult<Void> removeBanner(String id);
}
