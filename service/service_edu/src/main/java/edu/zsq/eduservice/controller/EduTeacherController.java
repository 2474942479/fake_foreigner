package edu.zsq.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.vo.TeacherQuery;
import edu.zsq.eduservice.service.EduTeacherService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * @CrossOrigin 跨域
 * @author zsq
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/eduService/teacher")
@Api(tags = {"讲师的CRUD"})
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询教师表所有数据
     * 特定异常测试
     *         Integer i=null;
     *         i.toString();
     * 自定义异常测试
     *         try {
     *             Integer i = null;
     *             i.toString();
     *
     *         } catch (NullPointerException e) {
     *             throw new MyException(20001, "i为null");
     *         }
     */
    @GetMapping("/findAll")
    @ApiOperation(value = "获取讲师列表")
    public JsonResult findAll() {
        List<EduTeacher> list = teacherService.list(null);
        return JsonResult.success().data("list", list);
    }

    /**
     * 无条件分页查询
     */
    @GetMapping("pageTeacher/{current}/{size}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, dataType = "Long", paramType = "path")
    })
    public JsonResult pageTeacher(@PathVariable Long current, @PathVariable Long size) {

        Page<EduTeacher> page = new Page<>(current, size);
        teacherService.page(page);

//        总记录数
        long total = page.getTotal();
//        每页数据List集合
        List<EduTeacher> list = page.getRecords();

        return JsonResult.success().data("total", total).data("list", list);
    }


    /**
     * 条件分页查询
     */
    @PostMapping("/getTeacherListPage/{current}/{size}")
    @ApiOperation(value = "讲师条件分页查询", notes = "根据获取的current size 以及teacherQuery查询并分页")

    public JsonResult getTeacherListPage(
            @ApiParam(name = "current", value = "当前页数", required = true)
            @PathVariable Long current,
            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery
    ) {
        Page<EduTeacher> page = new Page<>(current, size);
        teacherService.pageQuery(page, teacherQuery);
        //       分页后 单页页数据List集合
        List<EduTeacher> list = page.getRecords();
//        分页后查询到的全部记录数
        long total = page.getTotal();
        return JsonResult.success().data("total", total).data("list", list);
    }


    /**
     * 添加功能
     */
    @PostMapping("/addTeacher")
    @ApiOperation(value = "增加讲师", notes = "根据获取的EduTeacher对象新增讲师")
    @ApiImplicitParam(name = "teacherInfo", value = "讲师详细实体EduTeacher", required = true, dataType = "EduTeacher", paramType = "body")
    public JsonResult addTeacher(@RequestBody EduTeacher teacherInfo) {

        boolean b = teacherService.save(teacherInfo);
        if (b) {
            return JsonResult.success().message("添加成功");
        } else {
            return JsonResult.failure().message("添加失败");
        }
    }


    /**
     * 删除功能
     */

    @DeleteMapping("/removeTeacher/{id}")
    @ApiOperation(value = "删除讲师", notes = "根据获取的id删除讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true, dataType = "String", paramType = "path")
    public JsonResult removeTeacher(@PathVariable String id) {

        boolean b = teacherService.removeById(id);
        if (b) {
            return JsonResult.success().message("删除成功");
        } else {
            return JsonResult.failure().message("删除失败");
        }
    }


    /**
     * 修改前 根据id查询讲师  对数据进行回显
     */
    @GetMapping("/getTeacherInfo/{id}")
    public JsonResult getTeacherInfo(@PathVariable String id) {
        EduTeacher info = teacherService.getById(id);
        return JsonResult.success().data("info", info);
    }


    /**
     * 修改讲师 根据id查询讲师  对数据进行回显
     * <p>
     * 坑1:swagger2 paramType = "path" 表示在路径中取值 用于@PathVariable paramType = "body"  用于@RequestBody
     * 坑2    @RequestBody    只能用于除GetMapping中 Get取不到值
     */
    @PutMapping("/updateTeacher")
    @ApiOperation(value = "修改讲师", notes = "根据获取的EduTeacher对象修改讲师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "讲师Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "teacherInfo", value = "讲师详细实体EduTeacher", required = true, dataType = "EduTeacher", paramType = "body")
    })
    public JsonResult updateTeacher(@RequestBody EduTeacher teacherInfo) {
        boolean b = teacherService.updateById(teacherInfo);
        if (b) {
            return JsonResult.success().message("修改成功");
        } else {
            return JsonResult.failure().message("修改失败");
        }
    }

    /**
     * @ApiIgnore 使用该注解可以忽略这个API
     */
    @ApiIgnore
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }

}

