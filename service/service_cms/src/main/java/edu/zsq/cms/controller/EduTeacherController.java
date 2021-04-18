package edu.zsq.cms.controller;


import edu.zsq.cms.entity.dto.TeacherQueryDTO;
import edu.zsq.cms.entity.vo.TeacherAndCoursesVO;
import edu.zsq.cms.entity.vo.TeacherInfoVO;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.service.EduTeacherService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/eduCms/teacherFront")
public class EduTeacherController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    /**
     * 分页查询讲师信息
     *
     * @param teacherQueryDTO 查询条件
     * @return 讲师信息列表
     */
    @PostMapping("/getTeacherFront")
    public JsonResult<PageData<TeacherInfoVO>> getTeacherFront(@RequestBody TeacherQueryDTO teacherQueryDTO) {
        return JsonResult.success(teacherService.getTeacherFront(teacherQueryDTO));
    }


    /**
     * 根据讲师id获取讲师基本信息和所讲课程
     *
     * @param id 讲师id
     * @return 讲师信息以及所讲课程
     */
    @GetMapping("/getTeacherAllById/{id}")
    public JsonResult<TeacherAndCoursesVO> getTeacherAllById(@PathVariable String id) {

        TeacherAndCoursesVO teacherAndCoursesVO = TeacherAndCoursesVO.builder()
                .teacherInfo(teacherService.getById(id))
                .courseList(courseService.getCourseByTeacherId(id))
                .build();

        return JsonResult.success(teacherAndCoursesVO);
    }

}

