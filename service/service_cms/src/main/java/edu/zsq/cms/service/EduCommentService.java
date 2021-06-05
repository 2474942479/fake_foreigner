package edu.zsq.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.cms.entity.EduComment;
import edu.zsq.cms.entity.dto.CommentDTO;
import edu.zsq.cms.entity.dto.CommentQueryDTO;
import edu.zsq.cms.entity.vo.CommentVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据课程id查询评论列表
     *
     * @param commentQueryDTO 课程id
     * @return 评论信息
     */
    PageData<CommentVO> getCommentList(CommentQueryDTO commentQueryDTO);

    /**
     * 添加评论
     *
     * @param commentDTO 评论信息
     * @return 添加结果
     */
    void saveComment(CommentDTO commentDTO);
}
