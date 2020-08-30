package edu.zsq.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.cms.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     *  根据课程id查询评论列表
     * @param page
     * @param courseId
     * @param request
     * @return
     */
    Map<String, Object> getCommentList(Page<EduComment> page, String courseId,HttpServletRequest request);


}
