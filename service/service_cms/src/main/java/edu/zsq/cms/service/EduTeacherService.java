package edu.zsq.cms.service;

import edu.zsq.cms.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师列表
     * @param current 当前页数
     * @param size  每页记录数
     * @return
     */
    Map<String, Object> getTeacherFront(long current, long size);
}
