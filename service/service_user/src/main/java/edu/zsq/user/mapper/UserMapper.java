package edu.zsq.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zsq.user.entity.User;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zsq
 * @since 2020-08-21
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     * @param day
     * @return
     */
    Integer getRegisterNumber(String day);
}
