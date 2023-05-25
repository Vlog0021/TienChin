package com.javaboy.tienchin.common.constant;

public interface TienChinConstants {
    int CLUE_TYPE = 0;
    int BUSINESS_TYPE = 1;
    /**
     * 下次跟进时间，单位是小时
     */
    long NEXT_FOLLOW_TIME = 24;

    /**
     * 已分配
     */
    int CLUE_ALLOCATED = 1;

    /**
     * 进行中
     */
    int CLUE_FOLLOING = 2;

    /**
     * 已回收
     */
    int CLUE_RECOVERY = 3;

    /**
     * 伪线索
     */
    int CLUE_INVALIDATE = 4;
}
