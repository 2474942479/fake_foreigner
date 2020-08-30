package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduTeacher;
import edu.zsq.cms.mapper.EduTeacherMapper;
import edu.zsq.cms.service.EduTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFront(long current, long size) {
        Page<EduTeacher> page = new Page<>(current, size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(page, wrapper);
//        每页数据List集合
        List<EduTeacher> records = page.getRecords();
//        总记录数
        long total = page.getTotal();
//         每页显示条数
        long size1 = page.getSize();
//        当前分页总页数
        long pages = page.getPages();
//      当前页数
        long current1 = page.getCurrent();
//        是否存在下一页
        boolean next = page.hasNext();
//        是否存在上一页
        boolean previous = page.hasPrevious();
        Map<String, Object> map = new HashMap<>(7);
        map.put("records",records);
        map.put("total",total);
        map.put("size",size1);
        map.put("pages",pages);
        map.put("current",current1);
        map.put("previous",previous);
        map.put("next",next);

        return map;
    }
}
