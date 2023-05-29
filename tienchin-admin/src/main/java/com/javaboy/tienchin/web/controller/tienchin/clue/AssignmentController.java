package com.javaboy.tienchin.web.controller.tienchin.clue;

import com.javaboy.tienchin.assignment.domain.Assignment;
import com.javaboy.tienchin.assignment.service.IAssignmentService;
import com.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/tienchin/assignment")
public class AssignmentController {

    @Autowired
    private IAssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("@ss.hasPermi('tienchin:clue:assignment')")
    public AjaxResult assignClue(@Validated @RequestBody Assignment assignment) {
        return assignmentService.assignClue(assignment);
    }

    @PostMapping("/business")
    @PreAuthorize("@ss.hasPermi('tienchin:business:assignment')")
    public AjaxResult businessClue(@Validated @RequestBody Assignment assignment) {
        return assignmentService.assignClue(assignment);
    }
}
