package edu.zsq.eduservice.entity.dto.query;

import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.utils.page.PageData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangsongqi
 * @date 6:57 下午 2021/4/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BannerQueryDTO extends PageData<BannerVO> {
}
