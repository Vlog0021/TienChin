package com.javaboy.tienchin.course.service;

import com.javaboy.tienchin.common.core.domain.AjaxResult;
import com.javaboy.tienchin.course.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.course.vo.CourseVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-09
 */
public interface ICourseService extends IService<Course> {

    List<Course> selectCourseList(CourseVo courseVo);

    AjaxResult addCourse(Course course);

    AjaxResult updateCourse(Course course);

    boolean deleteCourseByIds(Long[] courseIds);
}
