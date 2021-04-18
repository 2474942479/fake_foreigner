package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.dto.CourseQueryDTO;
import edu.zsq.cms.entity.vo.CourseAllInfoVO;
import edu.zsq.cms.entity.vo.CourseInfoVO;
import edu.zsq.cms.entity.vo.CourseListVO;
import edu.zsq.cms.mapper.EduCourseMapper;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.wrapper.ChapterServiceWrapper;
import edu.zsq.cms.wrapper.OrderServiceWrapper;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
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

        String subjectParentId = courseQueryDTO.getSubjectParentId();
        String subjectId = courseQueryDTO.getSubjectId();
        String buyCountSort = courseQueryDTO.getBuyCountSort();
        String gmtCreateSort = courseQueryDTO.getGmtCreateSort();
        String priceSort = courseQueryDTO.getPriceSort();

        lambdaQuery()
                .eq(StringUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId)
                .eq(StringUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId)
                .eq(StringUtils.isEmpty(buyCountSort), EduCourse::getBuyCount, buyCountSort)
                .eq(StringUtils.isEmpty(gmtCreateSort), EduCourse::getGmtCreate, gmtCreateSort)
                .eq(StringUtils.isEmpty(priceSort), EduCourse::getPrice, priceSort)
                .eq(EduCourse::getStatus, "Normal")
                .page(coursePage);

        if (coursePage.getRecords().isEmpty()) {
            return PageData.of(Collections.emptyList(), coursePage.getCurrent(), coursePage.getSize(), coursePage.getTotal());
        }

        List<CourseListVO> courseList = coursePage.getRecords().stream().map(this::convertEduCourse).collect(Collectors.toList());

        return PageData.of(courseList, coursePage.getCurrent(), coursePage.getSize(), coursePage.getTotal());
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
                .subjectId(eduCourse.getSubjectId())
                .subjectParentId(eduCourse.getSubjectParentId())
                .teacherId(eduCourse.getTeacherId())
                .title(eduCourse.getTitle())
                .viewCount(eduCourse.getViewCount())
                .build();
    }

    @Override
    public CourseAllInfoVO getCourseAllInfo(String userId, String courseId) {

        // 根据课程id获取课程基本信息
        CourseInfoVO courseInfo = baseMapper.getCourseBaseInfo(courseId);

        // 根据课程id获取大纲信息
        List<ChapterVO> chapterList = chapterServiceWrapper.getAllChapterVO(courseId).getData();

        // 根据用户id和课程id判断用户是否购买课程
        Boolean isBuy = orderServiceWrapper.isBuyCourse(userId, courseId).getData();

        return CourseAllInfoVO.builder()
                .courseInfoVO(courseInfo)
                .chapterList(chapterList)
                .isBuy(isBuy)
                .build();

    }
}
