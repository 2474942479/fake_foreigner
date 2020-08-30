package edu.zsq.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 张
 * 与excel对应的实体类
 */

@Data
public class SubjectData {

    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;


}
