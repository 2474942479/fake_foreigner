package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduTeacher;
import edu.zsq.cms.entity.dto.TeacherQueryDTO;
import edu.zsq.cms.entity.vo.TeacherInfoVO;
import edu.zsq.cms.mapper.EduTeacherMapper;
import edu.zsq.cms.service.EduTeacherService;
import edu.zsq.utils.page.PageData;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public PageData<TeacherInfoVO> getTeacherList(TeacherQueryDTO teacherQueryDTO) {

        Page<EduTeacher> page = new Page<>(teacherQueryDTO.getCurrent(), teacherQueryDTO.getSize());
        lambdaQuery().orderByAsc(EduTeacher::getSort).page(page);

        // 判断查询结果是否为空,空直接返回
        if (page.getRecords().isEmpty()) {
            return PageData.of(Collections.emptyList(), page.getCurrent(), page.getSize(), page.getTotal());
        }

        // 不为空,将结果转换为VO进行返回
        List<TeacherInfoVO> orderLimitList = page.getRecords()
                .stream().map(this::convertEduTeacher).collect(Collectors.toList());

        return PageData.of(orderLimitList, page.getCurrent(), page.getSize(), page.getTotal());

    }

    private TeacherInfoVO convertEduTeacher(EduTeacher eduTeacher) {
        TeacherInfoVO teacherInfoVO = new TeacherInfoVO();
        teacherInfoVO.setId(eduTeacher.getId());
        teacherInfoVO.setAvatar(eduTeacher.getAvatar());
        teacherInfoVO.setIntro(eduTeacher.getIntro());
        teacherInfoVO.setCareer(eduTeacher.getCareer());
        teacherInfoVO.setLevel(eduTeacher.getLevel());
        teacherInfoVO.setName(eduTeacher.getName());
        teacherInfoVO.setSort(eduTeacher.getSort());
        return teacherInfoVO;
    }
}
