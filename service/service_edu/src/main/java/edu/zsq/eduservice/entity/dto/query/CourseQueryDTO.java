package edu.zsq.eduservice.entity.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.zsq.eduservice.entity.vo.CourseVO;
import edu.zsq.utils.page.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQueryDTO extends PageData<CourseVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "发布状态 1高级讲师 2首席讲师")
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private LocalDateTime begin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private LocalDateTime end;

}
