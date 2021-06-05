package edu.zsq.cms.service;

import edu.zsq.cms.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.cms.entity.dto.TeacherQueryDTO;
import edu.zsq.cms.entity.vo.TeacherInfoVO;
import edu.zsq.utils.page.PageData;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师列表
     * @param teacherQueryDTO 查询条件
     * @return 分页结果
     */
    PageData<TeacherInfoVO> getTeacherList(TeacherQueryDTO teacherQueryDTO);
}
