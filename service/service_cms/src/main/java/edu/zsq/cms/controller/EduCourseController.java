package edu.zsq.cms.controller;


import edu.zsq.cms.entity.dto.CourseQueryDTO;
import edu.zsq.cms.entity.vo.CourseAllInfoVO;
import edu.zsq.cms.entity.vo.CourseListVO;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/eduCms/course")
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    /**
     * 条件查询带分页
     *
     * @param courseQueryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/getCourseList")
    public JsonResult<PageData<CourseListVO>> getCourseList(@RequestBody CourseQueryDTO courseQueryDTO) {
        return JsonResult.success(courseService.getCourseListByQuery(courseQueryDTO));
    }

    /**
     * 获取课程详情页面所有信息
     *
     * @param courseId 课程id
     * @return 课程详情页面所有信息
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public JsonResult<CourseAllInfoVO> getCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        return JsonResult.success(courseService.getCourseInfo(JwtUtils.getMemberIdByJwtToken(request), courseId));
    }

}

