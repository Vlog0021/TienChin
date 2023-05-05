package com.javaboy.tienchin.channel.service.impl;

import com.javaboy.tienchin.channel.domain.Channel;
import com.javaboy.tienchin.channel.mapper.ChannelMapper;
import com.javaboy.tienchin.channel.service.IChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shmily
 * @since 2023-05-05
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> selectChannelList() {
        return channelMapper.selectChannelList();
    }
}
