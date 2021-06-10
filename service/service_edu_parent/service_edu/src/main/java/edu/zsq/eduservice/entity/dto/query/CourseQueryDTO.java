package edu.zsq.eduservice.entity.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.zsq.utils.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQueryDTO extends PageDTO {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private LocalDateTime begin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private LocalDateTime end;

}
