package edu.zsq.utils.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页参数
 *
 * @author zhangsongqi
 * @date 2:00 下午 2021/3/30
 */
public class PageDTO {

    @ApiModelProperty(value = "当前页 默认值1")
    private Long current;

    @ApiModelProperty(value = "每页显示条数 默认值30")
    private Long size;

    public Long getCurrent() {
        return null == current ? 1L : current;
    }

    public Long getSize() {
        return null == size ? 30L : size;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "current=" + current +
                ", size=" + size +
                '}';
    }
}
