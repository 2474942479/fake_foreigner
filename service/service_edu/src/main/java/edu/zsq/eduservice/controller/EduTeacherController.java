package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.dto.TeacherDTO;
import edu.zsq.eduservice.entity.dto.query.TeacherQueryDTO;
import edu.zsq.eduservice.entity.vo.TeacherInfoVO;
import edu.zsq.eduservice.service.EduTeacherService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsq
 * @CrossOrigin 跨域注解
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/eduService/teacher")
@Api(tags = {"讲师的CRUD"})
public class EduTeacherController {

    @Resource
    private EduTeacherService teacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "获取讲师列表")
    public JsonResult<List<EduTeacher>> findAll() {
        return JsonResult.success(teacherService.list(null));
    }

    /**
     * 无条件分页查询
     */
    /*
    @GetMapping("pageTeacher/{current}/{size}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, dataType = "Long", paramType = "path")
    })
    public JsonResult<PageData<TeacherInfoVO>> pageTeacher(@PathVariable Long current, @PathVariable Long size) {


        Page<EduTeacher> page = new Page<>(current, size);
        teacherService.page(page);


//        总记录数
        long total = page.getTotal();
//        每页数据List集合
        if (page.getRecords().isEmpty()) {
            return JsonResult.success(PageData.empty());
        }


        return JsonResult.success(PageData.of(list, current, size, total));
    }*/


    /**
     * 条件分页查询
     */
    @PostMapping("/getTeacherListPage")
    @ApiOperation(value = "讲师条件分页查询", notes = "根据获取的current size 以及teacherQuery查询并分页")

    public JsonResult<PageData<TeacherInfoVO>> getTeacherListPage(@RequestBody TeacherQueryDTO teacherQueryDTO) {
        return JsonResult.success(teacherService.getTeacherListPage(teacherQueryDTO));
    }


    /**
     * 添加教师
     *
     * @param teacherDTO 教师信息
     * @return 添加结果
     */
    @PostMapping("/addTeacher")
    @ApiOperation(value = "增加讲师", notes = "根据获取的EduTeacher对象新增讲师")
    @ApiImplicitParam(name = "teacherInfo", value = "讲师详细实体EduTeacher", required = true, dataType = "EduTeacher", paramType = "body")
    public JsonResult<Void> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.saveTeacher(teacherDTO);
    }


    /**
     * 删除教师
     *
     * @param id 教师id
     * @return 删除结果
     */
    @DeleteMapping("/removeTeacher/{id}")
    @ApiOperation(value = "删除讲师", notes = "根据获取的id删除讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true, dataType = "String", paramType = "path")
    public JsonResult<Void> removeTeacher(@PathVariable String id) {
        return teacherService.delTeacher(id);
    }


    /**
     * 根据id查询讲师
     *
     * @param id 教师id
     * @return 教师信息
     */
    @GetMapping("/getTeacherInfo/{id}")
    public JsonResult<TeacherInfoVO> getTeacherInfo(@PathVariable String id) {
        return JsonResult.success(teacherService.getTeacherInfo(id));
    }


    /**
     * 根据id修改讲师信息
     *
     * @param teacherDTO 教师信息
     * @return 修改结果
     */
    @PutMapping("/updateTeacher")
    @ApiOperation(value = "修改讲师", notes = "根据获取的EduTeacher对象修改讲师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "讲师Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "teacherInfo", value = "讲师详细实体EduTeacher", required = true, dataType = "EduTeacher", paramType = "body")
    })
    public JsonResult<Void> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(teacherDTO);
    }

}

