package edu.zsq.cms.entity.vo;

import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
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

    private CourseInfoVO courseInfoVO;
    private List<ChapterVO> chapterList;
    private Boolean isBuy;
}
