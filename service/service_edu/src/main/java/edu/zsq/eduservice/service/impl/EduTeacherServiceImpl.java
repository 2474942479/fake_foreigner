package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.vo.TeacherQuery;
import edu.zsq.eduservice.mapper.EduTeacherMapper;
import edu.zsq.eduservice.service.EduTeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //        坑 id为表中字段名

        wrapper.orderByDesc("gmt_create");
        if (teacherQuery != null) {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();

            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (level != null) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.le("gmt_modified", end);
            }
        }

//继承的ServiceImpl 自动注入了baseMapper
        baseMapper.selectPage(pageParam, wrapper);
    }
}
