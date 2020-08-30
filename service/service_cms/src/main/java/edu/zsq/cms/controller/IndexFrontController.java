package edu.zsq.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zsq.cms.entity.EduBanner;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.EduTeacher;
import edu.zsq.cms.service.EduBannerService;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.service.EduTeacherService;
import edu.zsq.utils.result.MyResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  前台首页
 * @author 张
 */
@RestController
@RequestMapping("/eduCms/indexFront")
public class IndexFrontController {

    @Autowired
    private EduBannerService bannerService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;


    /**
     * 注意 @Cacheable 的key两个引号
     * 缓存@Cacheable 多用于查询
     * 缓存@CachePut 多用于新增 会将添加的结果放进redis
     * @CacheEvict 多用于删除 会清空redis中指定的缓存
     *
     * @return
     */
    @Cacheable(key = "'selectIndexInfo'", value = "IndexInfo")
    @ApiOperation(value = "查询前两条幻灯片banner记录 前8条热门课程 前4个名师")
    @GetMapping("/getIndexInfo")
    public MyResultUtils getIndexInfo(){

//        根据id进行降序排列 只显示前两条幻灯片banner记录
        QueryWrapper<EduBanner> bannerWrapper = new QueryWrapper<>();
        bannerWrapper.orderByDesc("id");
        bannerWrapper.last("limit 2");

        List<EduBanner> bannerList = bannerService.list(bannerWrapper);

//        查询前8条热门课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

//        前4个名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return MyResultUtils.ok().data("bannerList",bannerList).data("courseList",courseList).data("teacherList",teacherList);
    }

}
