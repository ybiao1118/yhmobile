package com.yinhai.common.dao;

import com.yinhai.common.entity.ExteriorLog;
/** 接口调用日志
 * @author yanbiao
 * @since 2019/11/8 17:51
 */
public interface ExteriorLogMapper {
    /**
     * 新增接口调用记录日志
     * @param log 接口调用日志对象
     */
    void insert(ExteriorLog log);
}
