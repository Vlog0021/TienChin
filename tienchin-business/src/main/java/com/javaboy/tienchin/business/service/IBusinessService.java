package com.javaboy.tienchin.business.service;

import com.javaboy.tienchin.business.domain.Business;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.business.domain.vo.BusinessFollow;
import com.javaboy.tienchin.business.domain.vo.BusinessSummary;
import com.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-25
 */
public interface IBusinessService extends IService<Business> {

    List<BusinessSummary> selectBusinessList();

    AjaxResult addBusiness(Business business);

    AjaxResult getBusinessById(Integer id);

    AjaxResult follow(BusinessFollow businessFollow);
}
