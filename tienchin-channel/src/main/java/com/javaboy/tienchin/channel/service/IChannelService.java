package com.javaboy.tienchin.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.channel.domain.Channel;
import com.javaboy.tienchin.channel.vo.ChannelVo;
import com.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shmily
 * @since 2023-05-05
 */
public interface IChannelService extends IService<Channel> {

    List<Channel> selectChannelList(ChannelVo channelVo);

    AjaxResult addChannel(ChannelVo channelVo);

    AjaxResult updateChannel(ChannelVo channelVo);

    boolean deleteChannelByIds(Long[] channelIds);

    boolean importChannel(List<Channel> channelList, boolean updateSupport);
}
