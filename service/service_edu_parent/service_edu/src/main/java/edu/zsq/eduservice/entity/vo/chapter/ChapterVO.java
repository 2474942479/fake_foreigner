package edu.zsq.eduservice.entity.vo.chapter;

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
