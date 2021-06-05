package edu.zsq.cms.entity.dto;

import edu.zsq.cms.entity.vo.TeacherInfoVO;
import edu.zsq.utils.page.PageData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangsongqi
 * @date 7:17 下午 2021/4/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class TeacherQueryDTO extends PageData<TeacherInfoVO> {


}
