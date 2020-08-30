package edu.zsq.acl.service.impl;

import edu.zsq.acl.entity.User;
import edu.zsq.acl.mapper.UserMapper;
import edu.zsq.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
