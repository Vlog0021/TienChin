package com.javaboy.tienchin.business.mapper;

import com.javaboy.tienchin.business.domain.Business;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaboy.tienchin.business.domain.vo.BusinessSummary;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shmily
 * @since 2023-05-25
 */
public interface BusinessMapper extends BaseMapper<Business> {

    List<BusinessSummary> selectBusinessList();
}
