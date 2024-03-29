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
public class TeacherQueryDTO extends PageDTO {

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private LocalDateTime begin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private LocalDateTime end;

}
