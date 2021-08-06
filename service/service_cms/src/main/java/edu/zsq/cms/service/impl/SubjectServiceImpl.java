package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.Subject;
import edu.zsq.cms.mapper.SubjectMapper;
import edu.zsq.cms.service.SubjectService;
import org.springframework.stereotype.Service;

/**
 * @author 张
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
}
