package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduBanner;
import edu.zsq.eduservice.entity.dto.BannerDTO;
import edu.zsq.eduservice.entity.dto.query.BannerQueryDTO;
import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.eduservice.mapper.EduBannerMapper;
import edu.zsq.eduservice.service.EduBannerService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-24
 */
@Service
public class EduBannerServiceImpl extends ServiceImpl<EduBannerMapper, EduBanner> implements EduBannerService {

    @Override
    public PageData<BannerVO> getBannerList(BannerQueryDTO bannerQueryDTO) {
        Page<EduBanner> eduBannerPage = new Page<>(bannerQueryDTO.getCurrent(), bannerQueryDTO.getSize());
        lambdaQuery().page(eduBannerPage);

        if (eduBannerPage.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<BannerVO> collect = eduBannerPage.getRecords().stream()
                .map(this::convertEduBanner)
                .collect(Collectors.toList());

        return PageData.of(collect, eduBannerPage.getCurrent(), eduBannerPage.getSize(), eduBannerPage.getTotal());
    }

    @Override
    public BannerVO getBannerById(String id) {
        EduBanner one = lambdaQuery().eq(EduBanner::getId, id).last(Constants.LIMIT_ONE).one();
        return convertEduBanner(one);
    }

    @Override
    public void saveOrUpdateBanner(BannerDTO bannerDTO) {
        if (!saveOrUpdate(convertBannerDTO(bannerDTO))) {
            throw ExFactory.throwSystem("系统异常，添加失败");
        }
    }

    @Override
    public void removeBanner(String id) {
        if (!removeById(id)) {
            throw ExFactory.throwSystem("系统异常，修改失败");
        }
    }

    private BannerVO convertEduBanner(EduBanner eduBanner) {

        return BannerVO.builder()
                .id(eduBanner.getId())
                .imageUrl(eduBanner.getImageUrl())
                .linkUrl(eduBanner.getLinkUrl())
                .sort(eduBanner.getSort())
                .title(eduBanner.getTitle())
                .build();
    }

    private EduBanner convertBannerDTO(BannerDTO bannerDTO) {
        EduBanner eduBanner = new EduBanner();
        eduBanner.setTitle(bannerDTO.getTitle());
        eduBanner.setImageUrl(bannerDTO.getImageUrl());
        eduBanner.setLinkUrl(bannerDTO.getLinkUrl());
        eduBanner.setSort(bannerDTO.getSort());
        return eduBanner;
    }
}
