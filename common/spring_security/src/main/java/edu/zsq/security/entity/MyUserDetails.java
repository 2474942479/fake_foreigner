package edu.zsq.security.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 安全认证用户详情信息
 * </p>
 *
 * @author 张
 */
@Data
@Slf4j
public class MyUserDetails implements UserDetails {

    /**
     * 当前登录用户
     */
    private transient UserInfoDTO userInfo;

    /**
     * 当前权限
     */
    private List<String> permissionValueList;

    public MyUserDetails() {
    }

    public MyUserDetails(UserInfoDTO userInfoDTO, List<String> permissionValueList) {
        if (userInfoDTO != null) {
            this.userInfo = userInfoDTO;
        }

        this.permissionValueList = permissionValueList;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {

        return permissionValueList.parallelStream()
                .filter(StringUtils::isNoneBlank)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
