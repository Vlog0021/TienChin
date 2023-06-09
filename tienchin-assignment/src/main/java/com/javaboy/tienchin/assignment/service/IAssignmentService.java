package com.javaboy.tienchin.assignment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.assignment.domain.Assignment;
import com.javaboy.tienchin.common.core.domain.AjaxResult;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
public interface IAssignmentService extends IService<Assignment> {

    AjaxResult assignClue(Assignment assignment);
}
