package edu.zsq.eduservice.entity.vo.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程大纲
 *
 * @author 张
 */
@Data
public class ChapterVO {

    private String id;

    private String title;

    private List<VideoVO> children;

}
