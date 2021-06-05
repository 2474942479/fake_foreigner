package edu.zsq.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.Subject;
import edu.zsq.cms.entity.dto.CourseQueryDTO;
import edu.zsq.cms.entity.vo.CourseAllInfoVO;
import edu.zsq.cms.entity.vo.CourseListVO;
import edu.zsq.cms.entity.vo.CourseVO;
import edu.zsq.cms.mapper.EduCourseMapper;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.service.SubjectService;
import edu.zsq.cms.wrapper.ChapterServiceWrapper;
import edu.zsq.cms.wrapper.OrderServiceWrapper;
import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import edu.zsq.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private ChapterServiceWrapper chapterServiceWrapper;

    @Resource
    private OrderServiceWrapper orderServiceWrapper;

    @Resource
    private SubjectService subjectService;

    @Override
    public List<EduCourse> getCourseByTeacherId(String id) {

        return lambdaQuery()
                .eq(EduCourse::getTeacherId, id)
                .orderByDesc(EduCourse::getGmtModified)
                .list();
    }


    /**
     * 根据条件对课程进行分页查询
     *
     * @param courseQueryDTO 查询条件
     * @return 分页查询结果
     */
    @Override
    public PageData<CourseListVO> getCourseListByQuery(CourseQueryDTO courseQueryDTO) {

        Page<EduCourse> coursePage = new Page<>(courseQueryDTO.getCurrent(), courseQueryDTO.getSize());

        String subjectId = courseQueryDTO.getSubjectId();
        String teacherId = courseQueryDTO.getTeacherId();
        String buyCountSort = courseQueryDTO.getBuyCountSort();
        String gmtCreateSort = courseQueryDTO.getGmtCreateSort();
        String priceSort = courseQueryDTO.getPriceSort();

        lambdaQuery()
                .eq(StringUtils.isNotBlank(teacherId), EduCourse::getTeacherId, teacherId)
                .eq(EduCourse::getStatus, "Normal")
                .orderByDesc(StringUtils.isNotBlank(buyCountSort), EduCourse::getBuyCount)
                .orderByDesc(StringUtils.isNotBlank(gmtCreateSort), EduCourse::getGmtCreate)
                .orderByDesc(StringUtils.isNotBlank(priceSort), EduCourse::getPrice)
                .page(coursePage);

        if (coursePage.getRecords().isEmpty()) {
            return PageData.of(Collections.emptyList(), coursePage.getCurrent(), coursePage.getSize(), coursePage.getTotal());
        }

        List<CourseListVO> courseList = coursePage.getRecords().parallelStream()
                .filter(course -> {
                    if (StringUtils.isBlank(subjectId)) {
                        return true;
                    }
                    return JSON.parseArray(course.getSubjectIds(), String.class)
                            .parallelStream()
                            .anyMatch(s -> s.equals(subjectId));
                })
                .map(this::convertEduCourse)
                .collect(Collectors.toList());

        return PageData.of(courseList, coursePage.getCurrent(), coursePage.getSize(), coursePage.getTotal());
    }

    @Override
    public CourseAllInfoVO getCourseInfo(String userId, String courseId) {

        // 根据课程id获取课程基本信息
        CourseVO courseInfo = baseMapper.getCourseBaseInfo(courseId);

        List<String> subjectIds = JSON.parseArray(courseInfo.getSubjectIds(), String.class);
        String subjectName = subjectService.lambdaQuery()
                .in(Subject::getId, subjectIds)
                .select(Subject::getTitle)
                .orderByAsc(Subject::getGmtCreate)
                .list()
                .stream()
                .map(Subject::getTitle)
                .collect(Collectors.joining("\\"));


        // 根据课程id获取大纲信息
        List<ChapterVO> chapterList = chapterServiceWrapper.getAllChapterVO(courseId);

        // 根据用户id和课程id判断用户是否购买课程
        Boolean isBuy = orderServiceWrapper.isBuyCourse(userId, courseId);

        return CourseAllInfoVO.builder()
                .courseVO(courseInfo)
                .subjectName(subjectName)
                .chapterList(chapterList)
                .isBuy(isBuy)
                .build();

    }

    private CourseListVO convertEduCourse(EduCourse eduCourse) {

        return CourseListVO.builder()
                .id(eduCourse.getId())
                .cover(eduCourse.getCover())
                .buyCount(eduCourse.getBuyCount())
                .lessonNum(eduCourse.getLessonNum())
                .price(eduCourse.getPrice())
                .reductionMoney(eduCourse.getReductionMoney())
                .status(eduCourse.getStatus())
                .teacherId(eduCourse.getTeacherId())
                .title(eduCourse.getTitle())
                .viewCount(eduCourse.getViewCount())
                .build();
    }
}
