package com.yinhai.common.service.v5;

import com.yinhai.common.service.TestService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author yanbiao
 * @since 2020/4/10 10:58
 */
@Profile("${common.releaseV5}")
@Service
public class TestServiceImpl implements TestService {
}
