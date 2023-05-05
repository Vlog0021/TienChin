package com.javaboy.tienchin.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaboy.tienchin.channel.domain.Channel;

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

    List<Channel> selectChannelList();
}
