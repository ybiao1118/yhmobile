package com.yinhai.common.service.impl;


import com.yinhai.common.dao.ErrLogMapper;
import com.yinhai.common.dao.ExteriorLogMapper;
import com.yinhai.common.dao.OperationalLogMapper;
import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.entity.ExteriorLog;
import com.yinhai.common.entity.OperationalLog;
import com.yinhai.common.service.AsynLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**异步日志记录Service,
 * 使用 @Async("taskExecutor")注解实现线程记录日志
 * @author yanbiao
 * @since 2019/11/8 18:36
 */
@Service
public class AsynLogServiceImpl implements AsynLogService {
    @Resource
    private OperationalLogMapper operLogMapper;
    @Resource
    private ErrLogMapper errLogMapper;
    @Resource
    private ExteriorLogMapper exteriorLogMapper;
      @Override
    @Async("taskExecutor")
    public void insertOperationalLog(OperationalLog log) {
        operLogMapper.insert(log);
    }

    @Override
    @Async("taskExecutor")
    public void insertErrLog(ErrLog log) {
        errLogMapper.insert(log);
    }

    @Override
    @Async("taskExecutor")
    public void insertExteriorLog(ExteriorLog log) {
        exteriorLogMapper.insert(log);
    }

}
