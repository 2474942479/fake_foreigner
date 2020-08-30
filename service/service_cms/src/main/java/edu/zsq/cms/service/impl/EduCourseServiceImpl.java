package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.vo.CourseQueryVo;
import edu.zsq.cms.entity.vo.CourseWebVo;
import edu.zsq.cms.mapper.EduCourseMapper;
import edu.zsq.cms.service.EduCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Override
    public List<EduCourse> getCourseByTeacherId(String id) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }


    /**
     * 根据条件对课程进行分页查询
     *
     * @param page
     * @param courseQuery
     * @return
     */
    @Override
    public Map<String, Object> getCourseListByQuery(Page<EduCourse> page, CourseQueryVo courseQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //        坑 id为表中字段名

        if (courseQuery != null) {
            String subjectParentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();
            String buyCountSort = courseQuery.getBuyCountSort();
            String gmtCreateSort = courseQuery.getGmtCreateSort();
            String priceSort = courseQuery.getPriceSort();

            if (!StringUtils.isEmpty(subjectParentId)) {
                wrapper.eq("subject_parent_id", subjectParentId);
            }
            if (!StringUtils.isEmpty(subjectId)) {
                wrapper.eq("subject_id", subjectId);
            }

            if (!StringUtils.isEmpty(buyCountSort)) {
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(gmtCreateSort)) {
                wrapper.orderByDesc("gmt_create");
            }
            if (!StringUtils.isEmpty(priceSort)) {
                wrapper.orderByDesc("price");
            }
        }

        wrapper.eq("status","Normal");

        //继承的ServiceImpl 自动注入了baseMapper
        baseMapper.selectPage(page, wrapper);
        List<EduCourse> records = page.getRecords();
//        总记录数
        long total = page.getTotal();
//         每页显示条数
        long size1 = page.getSize();
//        当前分页总页数
        long pages = page.getPages();
//      当前页数
        long current1 = page.getCurrent();
//        是否存在下一页
        boolean next = page.hasNext();
//        是否存在上一页
        boolean previous = page.hasPrevious();
        Map<String, Object> map = new HashMap<>(7);
        map.put("records",records);
        map.put("total",total);
        map.put("size",size1);
        map.put("pages",pages);
        map.put("current",current1);
        map.put("previous",previous);
        map.put("next",next);

        return map;
    }

    /**
     * 获取课程详情页面所有信息
     * @param courseId 课程id
     * @return
     */
    @Override
    public CourseWebVo getCourseBaseInfo(String courseId) {
        CourseWebVo courseBaseInfo = baseMapper.getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }
}
