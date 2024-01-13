package io.github.ninobomba.commons.exceptions.core.factories;

import cn.danielw.fop.ObjectFactory;
import cn.danielw.fop.ObjectPool;
import cn.danielw.fop.PoolConfig;
import cn.danielw.fop.Poolable;

public final class ExceptionProviderFactoryPool<T> {

    private final Class<T> tClass;

    private final ObjectPool<T> objectPool;

    private final int POOL_PARTITION_SIZE = 5;
    private final int POOL_MIN_SIZE = 10;
    private final int POOL_MAX_SIZE = 15;
    private final int POOL_MAX_IDLE_MS = 60 * 1_000 * 5;

    public ExceptionProviderFactoryPool(Class<T> tClass) {
        this.tClass = tClass;
        objectPool = new ObjectPool<>( setup(POOL_PARTITION_SIZE, POOL_MAX_SIZE, POOL_MIN_SIZE, POOL_MAX_IDLE_MS), create() );
    }

    public ExceptionProviderFactoryPool(Class<T> tClass, int partition, int maxSize, int minSize, int maxIdleMilliseconds ) {
        this.tClass = tClass;
        objectPool = new ObjectPool<>( setup( partition, maxSize, minSize, maxIdleMilliseconds ), create() );
    }

    private PoolConfig setup(int partition, int maxSize, int minSize, int maxIdleMilliseconds) {
        var configuration = new PoolConfig();
        configuration.setPartitionSize( partition );
        configuration.setMaxSize( maxSize );
        configuration.setMinSize( minSize );
        configuration.setMaxIdleMilliseconds( maxIdleMilliseconds );
        return configuration;
    }

    private ObjectFactory<T> create() {
        return new ObjectFactory<>() {
            @Override
            public T create() {
                try {
                    return tClass.getConstructor().newInstance();
                } catch ( Exception e ){
                    return (T) new RuntimeException();
                }
            }

            @Override
            public void destroy(Object o) {
            }

            @Override
            public boolean validate(Object o) {
                return true;
            }

        };

    }

    public T getException() {
        try ( Poolable<?> poolable = objectPool.borrowObject() ) {
            return (T) poolable.getObject();
        }
    }

    public int getSize(){
        return objectPool.getSize();
    }

    public void returnObject( Poolable<?> instance ) {
        objectPool.returnObject((Poolable<T>) instance);
    }

    public void shutdown() throws InterruptedException {
        objectPool.shutdown();
    }

}
