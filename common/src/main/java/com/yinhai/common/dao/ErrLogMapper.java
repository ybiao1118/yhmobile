package com.yinhai.common.dao;

import com.yinhai.common.entity.ErrLog;

/**
 * @author yanbiao
 * @since 2019/11/8 17:51
 */
public interface ErrLogMapper {
    /**
     * 新增异常记录日志
     * @param log 异常日志对象
     */
    void insert(ErrLog log);
}
