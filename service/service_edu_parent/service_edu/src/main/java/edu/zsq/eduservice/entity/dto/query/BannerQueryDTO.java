package edu.zsq.eduservice.entity.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.zsq.eduservice.entity.vo.BannerVO;
import edu.zsq.utils.page.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author zhangsongqi
 * @date 6:57 下午 2021/4/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BannerQueryDTO extends PageData<BannerVO> {

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private LocalDateTime begin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private LocalDateTime end;
}
