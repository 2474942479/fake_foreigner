package edu.zsq.user.entity.dto;

import lombok.Data;

/**
 * 重置密码
 *
 * @author 张
 */
@Data
public class ResetDTO {

    private String id;
    private String oldPass;
    private String pass;
    private String mobile;
    private String code;
}
