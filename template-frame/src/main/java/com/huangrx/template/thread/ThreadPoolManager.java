package com.huangrx.template.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 异步任务管理器
 *
 * @author valarchie
 */
@Slf4j
public class ThreadPoolManager {

    /**
     * 使用SynchronousQueue队列，线程池的最大线程数无效，在任务提交时会立即执行任务（如果有可用线程），
     * 或者会创建新线程来处理任务（如果当前线程数小于核心线程数），或者根据策略拒绝任务（如果达到最大线程数并且队列已满）
     */
    private static final ThreadPoolExecutor THREAD_EXECUTOR = new ThreadPoolExecutor(
            ThreadConfig.CORE_POOL_SIZE, ThreadConfig.MAX_POOL_SIZE,
            ThreadConfig.KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * ScheduledExecutorService 是用于执行任务调度的接口，它允许在指定的延迟后执行任务，或者定期执行任务。
     * ScheduledThreadPoolExecutor 是 ScheduledExecutorService 的一个实现，它可以执行延迟任务或定期任务，并使用线程池来管理执行这些任务的线程。
     */
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = new ScheduledThreadPoolExecutor(
            ThreadConfig.CORE_POOL_SIZE,
            new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build(),
            new ThreadPoolExecutor.CallerRunsPolicy()) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (t == null && r instanceof Future<?> future) {
                try {
                    if (future.isDone()) {
                        future.get();
                    }
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            if (t != null) {
                log.error(t.getMessage(), t);
            }
        }
    };

    private ThreadPoolManager() {
    }

    /**
     * 执行schedule任务
     */
    public static void schedule(TimerTask task) {
        SCHEDULED_EXECUTOR.schedule(task, ThreadConfig.OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行异步任务任务
     */
    public static void execute(Runnable task) {
        THREAD_EXECUTOR.execute(task);
    }

    /**
     * 停止任务线程池
     */
    public static void shutdown() {
        THREAD_EXECUTOR.shutdown();
        SCHEDULED_EXECUTOR.shutdown();
    }
}
