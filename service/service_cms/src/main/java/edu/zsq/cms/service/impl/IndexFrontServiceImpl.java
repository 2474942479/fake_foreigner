package edu.zsq.cms.service.impl;

import edu.zsq.cms.entity.EduBanner;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.EduTeacher;
import edu.zsq.cms.entity.vo.IndexFrontVO;
import edu.zsq.cms.service.EduBannerService;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.service.EduTeacherService;
import edu.zsq.cms.service.IndexFrontService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@Service
public class IndexFrontServiceImpl implements IndexFrontService {

    @Resource
    private EduBannerService bannerService;
    @Resource
    private EduCourseService courseService;
    @Resource
    private EduTeacherService teacherService;

    /**
     * 注意 @Cacheable 的key两个引号
     * 缓存@Cacheable 多用于查询
     * 缓存@CachePut 多用于新增 会将添加的结果放进redis
     *
     * @return 查询数据
     * @CacheEvict 多用于删除 会清空redis中指定的缓存
     */

    @Override
    @Cacheable(key = "'getIndexInfo'", value = "IndexInfo")
    public IndexFrontVO getIndexInfo() {

        // 根据id进行降序排列 只显示前两条幻灯片banner记录
        List<EduBanner> bannerList = bannerService.lambdaQuery()
                .orderByDesc(EduBanner::getId)
                .last("limit 6")
                .list();

        // 查询前8条热门课程
        List<EduCourse> courseList = courseService.lambdaQuery()
                .eq(EduCourse::getStatus, "Normal")
                .orderByDesc(EduCourse::getId)
                .last("limit 8")
                .list();

        // 前4个名师
        List<EduTeacher> teacherList = teacherService.lambdaQuery()
                .orderByDesc(EduTeacher::getId)
                .last("limit 4")
                .list();

        IndexFrontVO indexFrontVO = new IndexFrontVO();
        indexFrontVO.setBannerList(bannerList);
        indexFrontVO.setCourseList(courseList);
        indexFrontVO.setTeacherList(teacherList);
        return indexFrontVO;
    }
}
