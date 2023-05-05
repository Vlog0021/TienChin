package com.javaboy.tienchin.channel.mapper;

import com.javaboy.tienchin.channel.domain.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shmily
 * @since 2023-05-05
 */
public interface ChannelMapper extends BaseMapper<Channel> {

    List<Channel> selectChannelList();
}
