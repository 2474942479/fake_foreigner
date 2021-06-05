package edu.zsq.cms.controller;


import edu.zsq.cms.entity.dto.CommentDTO;
import edu.zsq.cms.entity.dto.CommentQueryDTO;
import edu.zsq.cms.entity.vo.CommentVO;
import edu.zsq.cms.service.EduCommentService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/eduCms/comment")
public class EduCommentController {

    @Resource
    private EduCommentService commentService;

    /**
     * 根据课程id分页查询评论列表
     *
     * @param commentQueryDTO 分页条件
     * @return 评论列表
     */
    @ApiOperation(value = "评论分页列表")
    @PostMapping("/getCommentList")
    public JsonResult<PageData<CommentVO>> getCommentList(@RequestBody CommentQueryDTO commentQueryDTO) {
        return JsonResult.success(commentService.getCommentList(commentQueryDTO));
    }

    /**
     * 发表评论
     *
     * @param commentDTO 评论信息
     * @return 添加结果
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/saveCommentInfo")
    public JsonResult<Void> saveComment(@RequestBody CommentDTO commentDTO) {
        commentService.saveComment(commentDTO);
        return JsonResult.success();

    }

}

