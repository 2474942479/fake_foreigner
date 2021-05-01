package edu.zsq.security.provider;

import edu.zsq.security.security.DefaultPasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 张
 */
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;
    private DefaultPasswordEncoder passwordEncoder;

    public MyAuthenticationProvider() {

    }

    public MyAuthenticationProvider(UserDetailsService userDetailsService, DefaultPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 校验密码有效性.
     *
     * @param userDetails    .
     * @param authentication .
     * @throws AuthenticationException .
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "密码不能为空, 请输入密码"));
        }

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            throw new InternalAuthenticationServiceException("密码错误，请重新输入!");
        }
    }


    /**
     * 获取用户信息
     *
     * @param username       用户名
     * @param authentication 验证
     * @return UserDetails
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            return userDetailsService.loadUserByUsername(username);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

}
