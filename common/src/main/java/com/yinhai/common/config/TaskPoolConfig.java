package com.yinhai.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**线程池配置类
 * @author yanbiao
 * @since 2019/11/28 9:33
 */
@Configuration
@EnableAsync
@PropertySource("classpath:common.properties")
public class TaskPoolConfig {
    @Value("${task.pool.core_pool_size}")
    private int corePoolSize;
    @Value("${task.pool.max_pool_size}")
    private int maxPoolSize;
    @Value("${task.pool.queue_capacity}")
    private int queueCapacity;
    @Value("${task.pool.name_prefix}")
    private String namePrefix;
    @Value("${task.pool.keep_alive_seconds}")
    private int keepAliveSeconds;

    @Bean(name = "taskExecutor")
    public Executor asyncServiceExecutor() {
        //System.out.println("start taskExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数5：线程池创建时候初始化的线程数
        executor.setCorePoolSize(corePoolSize);
        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(maxPoolSize);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(queueCapacity);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix(namePrefix);
        executor.initialize();
        return executor;
    }
}
