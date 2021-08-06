package edu.zsq.cms.entity.vo;

import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhangsongqi
 * @date 11:23 下午 2021/4/18
 */
@Data
@Builder
public class CourseAllInfoVO {

    private CourseVO courseVO;
    private String subjectName;
    private List<ChapterVO> chapterList;
    private Boolean isBuy;
}
