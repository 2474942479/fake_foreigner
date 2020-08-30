package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页条件查询
     * @param pageParam 分页
     * @param teacherQuery  条件
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

}
