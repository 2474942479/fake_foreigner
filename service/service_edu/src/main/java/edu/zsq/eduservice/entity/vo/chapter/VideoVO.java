package edu.zsq.eduservice.entity.vo.chapter;

import lombok.Builder;
import lombok.Data;

/**
 * @author 张
 */
@Data
@Builder
public class VideoVO {

    private String id;

    private String chapterId;

    private String title;

    private String videoSourceId;
}
