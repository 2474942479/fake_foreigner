package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.dto.TeacherDTO;
import edu.zsq.eduservice.entity.dto.query.TeacherQueryDTO;
import edu.zsq.eduservice.entity.vo.TeacherVO;
import edu.zsq.utils.page.PageData;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-05-09
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页条件查询
     *
     * @param teacherQueryDTO 查询条件
     * @return 教师信息分页结果
     */
    PageData<TeacherVO> getTeacherListPage(TeacherQueryDTO teacherQueryDTO);

    /**
     * 添加教师
     *
     * @param teacherDTO 教师信息
     */
    void saveOrUpdateTeacher(TeacherDTO teacherDTO);

    /**
     * 删除教师
     *
     * @param id 教师id
     */
    void delTeacher(String id);

    /**
     * 查询单个教师信息
     *
     * @param id 教师id
     * @return 教师信息
     */
    TeacherVO getTeacherInfo(String id);
}
