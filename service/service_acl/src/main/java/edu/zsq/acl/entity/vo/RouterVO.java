package edu.zsq.acl.entity.vo;

import edu.zsq.utils.tree.TreeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 张
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RouterVO extends TreeDTO {

    @ApiModelProperty(value = "路由唯一键")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现")
    private Boolean hidden;

    @ApiModelProperty(value = "重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    @ApiModelProperty(value = "组件地址")
    private String component;

    @ApiModelProperty(value = "其他元素")
    private MetaVO meta;

    @ApiModelProperty(value = "当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面")
    private Boolean alwaysShow;

}
