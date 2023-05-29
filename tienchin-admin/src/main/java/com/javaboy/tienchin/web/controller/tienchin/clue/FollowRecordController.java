package com.javaboy.tienchin.web.controller.tienchin.clue;

import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.follow.service.IFollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/tienchin/clue/follow/record")
public class FollowRecordController {

    @Autowired
    private IFollowRecordService followRecordService;

    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @GetMapping("/{clueId}")
    public AjaxResult getFollowRecordByClueId(@PathVariable Integer clueId) {
        return followRecordService.getFollowRecordByClueId(clueId);
    }
}
