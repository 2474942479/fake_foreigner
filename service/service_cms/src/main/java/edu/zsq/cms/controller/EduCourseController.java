package edu.zsq.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.vo.CourseQueryVo;
import edu.zsq.cms.entity.vo.CourseWebVo;
import edu.zsq.cms.frign.ChapterService;
import edu.zsq.cms.frign.OrderService;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/eduCms/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderService orderService;
    /**
     * 条件查询带分页
     * @param current
     * @param size
     * @param courseQuery
     * @return
     */
    @PostMapping("/getCourseList/{current}/{size}")
    public MyResultUtils getCourseList(@PathVariable long current, @PathVariable long size, @RequestBody(required = false) CourseQueryVo courseQuery){

        Page<EduCourse> page = new Page<>(current,size);
        Map<String,Object> map = courseService.getCourseListByQuery(page,courseQuery);
        return MyResultUtils.ok().data(map);
    }

    /**
     * 获取课程详情页面所有信息
     * @param courseId 课程id
     * @return
     */
     @GetMapping("/getCourseAllInfo/{courseId}")
    public MyResultUtils getCourseAllInfo(@PathVariable String courseId, HttpServletRequest request){

//        根据课程id获取课程基本信息
        CourseWebVo courseBaseInfo = courseService.getCourseBaseInfo(courseId);

//         根据课程id获取大纲信息
         MyResultUtils allChapterVo = chapterService.getAllChapterVo(courseId);

//        根据用户id和课程id判断用户是否购买课程
         String userId = JwtUtils.getMemberIdByJwtToken(request);

         Boolean isBuy = orderService.isBuyCourse(userId, courseId);

         return allChapterVo.data("courseBaseInfo",courseBaseInfo).data("isBuy",isBuy);
     }





}

