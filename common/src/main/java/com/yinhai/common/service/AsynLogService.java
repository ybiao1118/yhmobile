package com.yinhai.common.service;


import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.entity.ExteriorLog;
import com.yinhai.common.entity.OperationalLog;

/**
 * @author yanbiao
 * @since 2019/11/8 18:35
 */
public interface AsynLogService {
    /**
     * 新增操作日志记录
     * @param log 操作日志对象
     */
    void insertOperationalLog(OperationalLog log);

    /**
     * 新增异常日志记录
     * @param log
     */
    void insertErrLog(ErrLog log);

    /**
     * 新增接口调用日志记录
     * @param log
     */
    void insertExteriorLog(ExteriorLog log);




}
