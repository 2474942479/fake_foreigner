package edu.zsq.eduservice.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangsongqi
 * @date 11:48 上午 2021/4/19
 */
@Data
@Builder
public class UserInfoVO {

    private String name;
    private String roles;
    private String avatar;
}
