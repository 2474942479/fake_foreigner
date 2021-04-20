package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.dto.TeacherDTO;
import edu.zsq.eduservice.entity.dto.query.TeacherQueryDTO;
import edu.zsq.eduservice.entity.vo.TeacherInfoVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页条件查询
     *
     * @param teacherQueryDTO 查询条件
     * @return 教师信息分页结果
     */
    PageData<TeacherInfoVO> getTeacherListPage(TeacherQueryDTO teacherQueryDTO);

    /**
     * 添加教师
     *
     * @param teacherDTO 教师信息
     * @return 添加结果
     */
    JsonResult<Void> saveTeacher(TeacherDTO teacherDTO);

    /**
     * 删除教师
     *
     * @param id 教师id
     * @return 删除结果
     */
    JsonResult<Void> delTeacher(String id);

    /**
     * 查询单个教师信息
     *
     * @param id 教师id
     * @return 教师信息
     */
    TeacherInfoVO getTeacherInfo(String id);

    /**
     * 根据教师id修改教师信息
     *
     * @param teacherDTO 教师信息
     * @return 修改结果
     */
    JsonResult<Void> updateTeacher(TeacherDTO teacherDTO);
}
