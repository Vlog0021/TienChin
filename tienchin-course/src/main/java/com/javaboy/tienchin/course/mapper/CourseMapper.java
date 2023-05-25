package com.javaboy.tienchin.course.mapper;

import com.javaboy.tienchin.course.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaboy.tienchin.course.vo.CourseVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shmily
 * @since 2023-05-09
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> selectCourseList(CourseVo courseVo);
}
