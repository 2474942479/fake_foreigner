package edu.zsq.acl.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhangsongqi
 * @date 6:31 下午 2021/3/30
 */
@Data
public class UserInfoVO {

    private String name;

    private String avatar;

    private List<String> roleNameList;

    private List<String> permissionValueList;
}
