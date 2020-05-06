package com.tinyv.sc.driver.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mayue
 */

public enum LocalThreadPool {

    Instance;

    /** 保持活跃的线程数 */
    public static final int CORE_POOL_SIZE = 100;

    /**  默认最大线程数 */
    public static final int MAXIMUM_POOL_SIZE = 500;

    public ThreadPoolExecutor executor;

    LocalThreadPool(){

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("MyExecutor-Pool-%d").build();

        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }

}
