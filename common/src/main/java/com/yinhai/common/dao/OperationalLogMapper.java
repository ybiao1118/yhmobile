package com.yinhai.common.dao;


import com.yinhai.common.entity.OperationalLog;

/**
 * @author yanbiao
 * @since 2019/11/8 17:52
 */
public interface OperationalLogMapper {
    /**
     * 新增操作日志记录
     * @param log 操作日志对象
     */
    void insert(OperationalLog log);
}
