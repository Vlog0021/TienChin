package com.javaboy.tienchin.clue.mapper;

import com.javaboy.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaboy.tienchin.clue.domain.vo.ClueDetails;
import com.javaboy.tienchin.clue.domain.vo.ClueSummary;
import com.javaboy.tienchin.clue.domain.vo.ClueVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shmily
 * @since 2023-05-23
 */
public interface ClueMapper extends BaseMapper<Clue> {

    List<ClueSummary> selectClueList(ClueVo clueVo);

    ClueDetails getClueDetailsByClueId(Integer clueId);
}
