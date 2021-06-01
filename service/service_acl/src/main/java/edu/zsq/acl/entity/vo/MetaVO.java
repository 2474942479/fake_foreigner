package edu.zsq.acl.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张
 */
@Data
public class MetaVO {

    @ApiModelProperty(value = "设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    @ApiModelProperty(value = "设置该路由的图标")
    private String icon;

    @ApiModelProperty(value = "设置为true，则不会被 <keep-alive>缓存")
    private Boolean noCache;
}
