package edu.zsq.cms.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangsongqi
 * @date 11:23 下午 2021/4/18
 */
@Data
@Builder
public class CourseAllInfoVO {

    private CourseDTO courseDTO;
//    private List<ChapterVO> chapterList;
    private Boolean isBuy;
}
