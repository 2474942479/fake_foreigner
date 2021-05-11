package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.dto.query.CourseQueryDTO;
import edu.zsq.eduservice.entity.dto.CourseDTO;
import edu.zsq.eduservice.entity.vo.CourseVO;
import edu.zsq.eduservice.entity.vo.FinalReleaseVO;
import edu.zsq.eduservice.service.EduCourseService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/eduService/course")
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    /**
     * 查询所有课程
     *
     * @return 课程列表
     */
    @GetMapping("/getAllCourse")
    public JsonResult<List<CourseVO>> getAllCourse() {
        return JsonResult.success(eduCourseService.getAllCourse());
    }

    /**
     * 条件分页查询
     */
    @PostMapping("/getCourseListPage")
    @ApiOperation(value = "课程条件分页查询", notes = "根据获取的current size 以及courseQuery查询并分页")
    public JsonResult<PageData<CourseVO>> getCourseListPage(@RequestBody CourseQueryDTO courseQueryDTO) {
        return JsonResult.success(eduCourseService.getCourseListPage(courseQueryDTO));
    }

    /**
     * 添加课程基本信息
     *
     * @param courseDTO 课程基本信息
     * @return 添加结果
     */
    @PostMapping("/addCourseInfo")
    public JsonResult<Void> addCourseInfo(@RequestBody CourseDTO courseDTO) {
        return eduCourseService.saveCourse(courseDTO);
    }


    /**
     * 根据课程id获取单个课程基本信息
     *
     * @param id 课程id
     * @return 课程基本信息
     */
    @GetMapping("/getCourseInfoById/{id}")
    public JsonResult<CourseVO> getCourseInfoById(@PathVariable String id) {
        return JsonResult.success(eduCourseService.getCourseInfoById(id));
    }

    /**
     * 修改课程基本信息
     *
     * @param courseDTO 课程基本信息VO类
     * @return 修改结果
     */
    @PutMapping("/updateCourseInfo")
    public JsonResult<Void> updateCourseInfo(@RequestBody CourseDTO courseDTO) {
        eduCourseService.updateCourseDTO(courseDTO);
        return JsonResult.OK;
    }

    /**
     * 根据课程id查询返回最终发布信息
     *
     * @param id 课程id
     * @return 最终发布信息
     */
    @GetMapping("/getFinalReleaseVo/{id}")
    public JsonResult<FinalReleaseVO> getFinalReleaseVo(@PathVariable String id) {
        return JsonResult.success(eduCourseService.getFinalReleaseVo(id));
    }

    /**
     * 修改课程发布状态
     *
     * @param courseDTO 发布状态
     * @return 修改结果
     */
    @PostMapping("/updateReleaseStatus")
    public JsonResult<Void> updateReleaseStatus(@RequestBody CourseDTO courseDTO) {
        return eduCourseService.updateReleaseStatus(courseDTO);
    }

    /**
     * 根据课程id删除课程相关的所有信息(课程章节 小节 描述 课程基本信息)
     *
     * @param courseId 课程id
     * @return 返回是否全部删除
     */
    @DeleteMapping("/deleteCourse/{courseId}")
    public JsonResult<Void> deleteCourse(@PathVariable String courseId) {
        return eduCourseService.removeCourseAllById(courseId);
    }

}

