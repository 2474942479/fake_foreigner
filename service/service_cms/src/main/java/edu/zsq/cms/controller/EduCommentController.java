package edu.zsq.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.cms.entity.EduComment;
import edu.zsq.cms.service.EduCommentService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.PAData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/eduCms/comment")
public class EduCommentController {

    @Resource
    private EduCommentService commentService;

    /**
     *  根据课程id分页查询评论列表
     * @param current
     * @param size
     * @return
     */
    @ApiOperation(value = "评论分页列表")
    @GetMapping("/{current}/{size}/{courseId}")
    public JsonResult getCommentList(@PathVariable long current, @PathVariable long size, @PathVariable String courseId, HttpServletRequest request){
        Page<EduComment> page = new Page<>(current,size);
        return JsonResult.success(commentService.getCommentList(page,courseId,request));
    }

    /**
     * 根据前端获取的用户id 和评论信息
     * @param commentInfo
     * @return
     */

    @ApiOperation(value = "添加评论")
    @PostMapping("/saveCommentInfo")
    public JsonResult<Void> saveComment(@RequestBody EduComment commentInfo){
        if (commentInfo.getUserId() == null ||"".equals(commentInfo.getUserId())){
            return JsonResult.failure("请先登录");
        }
        if (commentInfo.getContent() == null ||"".equals(commentInfo.getContent())){
            return JsonResult.failure("请输入评论内容");
        }
        if (commentService.save(commentInfo)){
            return JsonResult.OK;
        }else {
            return JsonResult.failure("发表评论失败");
        }

    }


}

