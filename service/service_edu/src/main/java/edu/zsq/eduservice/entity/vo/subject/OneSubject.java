package edu.zsq.eduservice.entity.vo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    /**
     * 一个以及分类有多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
