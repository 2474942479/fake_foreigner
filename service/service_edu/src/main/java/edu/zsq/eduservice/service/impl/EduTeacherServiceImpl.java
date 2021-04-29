package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.dto.TeacherDTO;
import edu.zsq.eduservice.entity.dto.query.TeacherQueryDTO;
import edu.zsq.eduservice.entity.vo.TeacherInfoVO;
import edu.zsq.eduservice.mapper.EduTeacherMapper;
import edu.zsq.eduservice.service.EduTeacherService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public PageData<TeacherInfoVO> getTeacherListPage(TeacherQueryDTO teacherQueryDTO) {

        Page<EduTeacher> teacherPage = new Page<>(teacherQueryDTO.getCurrent(), teacherQueryDTO.getSize());

        lambdaQuery()
                .eq(StringUtils.isNoneBlank(teacherQueryDTO.getName()), EduTeacher::getName, teacherQueryDTO.getName())
                .eq(Objects.nonNull(teacherQueryDTO.getLevel()), EduTeacher::getLevel, teacherQueryDTO.getLevel())
                .ge(Objects.nonNull(teacherQueryDTO.getBegin()), EduTeacher::getGmtCreate, teacherQueryDTO.getBegin())
                .le(Objects.nonNull(teacherQueryDTO.getEnd()), EduTeacher::getGmtModified, teacherQueryDTO.getEnd())
                .page(teacherPage);

        if (teacherPage.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<TeacherInfoVO> list = teacherPage.getRecords().stream()
                .map(this::convertEduTeacher)
                .collect(Collectors.toList());

        return PageData.of(list, teacherPage.getCurrent(), teacherPage.getSize(), teacherPage.getTotal());
    }

    @Override
    public JsonResult<Void> saveTeacher(TeacherDTO teacherDTO) {
        if (!save(convertTeacherDTO(teacherDTO))) {
            throw ExFactory.throwSystem("服务器错误, 教师添加失败！");
        }
        return JsonResult.OK;
    }

    @Override
    public JsonResult<Void> delTeacher(String id) {
        if (this.removeById(id)) {
            throw ExFactory.throwSystem("服务器错误, 教师删除失败！");
        }
        return JsonResult.OK;
    }

    @Override
    public TeacherInfoVO getTeacherInfo(String id) {
        return convertEduTeacher(getById(id));
    }

    @Override
    public JsonResult<Void> updateTeacher(TeacherDTO teacherDTO) {
        boolean updateResult = lambdaUpdate()
                .eq(EduTeacher::getId, teacherDTO.getId())
                .update();
        if (!updateResult) {
            throw ExFactory.throwSystem("服务器错误, 教师修改失败！");
        }
        return JsonResult.OK;
    }

    private EduTeacher convertTeacherDTO(TeacherDTO teacherDTO) {
        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setAvatar(teacherDTO.getAvatar());
        eduTeacher.setCareer(teacherDTO.getCareer());
        eduTeacher.setIntro(teacherDTO.getIntro());
        eduTeacher.setLevel(teacherDTO.getLevel());
        eduTeacher.setName(teacherDTO.getName());
        eduTeacher.setSort(teacherDTO.getSort());
        return eduTeacher;
    }

    private TeacherInfoVO convertEduTeacher(EduTeacher eduTeacher) {
        return TeacherInfoVO.builder()
                .id(eduTeacher.getId())
                .avatar(eduTeacher.getAvatar())
                .career(eduTeacher.getCareer())
                .intro(eduTeacher.getIntro())
                .sort(eduTeacher.getSort())
                .level(eduTeacher.getLevel())
                .name(eduTeacher.getName())
                .build();
    }
}
