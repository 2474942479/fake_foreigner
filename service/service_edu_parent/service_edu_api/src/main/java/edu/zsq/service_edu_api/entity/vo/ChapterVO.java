package edu.zsq.service_edu_api.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 课程大纲
 *
 * @author 张
 */
@Data
@Builder
public class ChapterVO {

    private String id;

    private String title;

    private String description;

    private List<VideoVO> videoVOList;

}
