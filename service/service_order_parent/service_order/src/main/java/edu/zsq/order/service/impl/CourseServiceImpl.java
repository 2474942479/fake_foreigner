package edu.zsq.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.order.entity.Course;
import edu.zsq.order.mapper.CourseMapper;
import edu.zsq.order.service.CourseService;
import org.springframework.stereotype.Service;

/**
 * @author 张
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
