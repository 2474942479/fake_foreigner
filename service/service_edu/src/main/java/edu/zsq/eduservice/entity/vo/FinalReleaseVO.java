package edu.zsq.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 张
 */
@Data
public class FinalReleaseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String courseId;
    private String title;
    private String cover;
    private String description;
    private Integer lessonNum;
    private String oneSubjectTitle;
    private String twoSubjectTitle;
    private String teacherName;
    /**
     *     只用于显示
     */

    private String price;
    private String reductionMoney;
}
