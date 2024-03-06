package io.github.ninobomba.commons.exceptions.core.factories;

import cn.danielw.fop.ObjectFactory;
import cn.danielw.fop.ObjectPool;
import cn.danielw.fop.PoolConfig;
import cn.danielw.fop.Poolable;
import lombok.SneakyThrows;

public final class ExceptionFactoryPool < T > {
	private static final int DEFAULT_POOL_PARTITION_SIZE = 2;
	private static final int DEFAULT_POOL_MIN_SIZE       = 10;
	private static final int DEFAULT_POOL_MAX_SIZE       = 15;
	private static final int DEFAULT_POOL_MAX_IDLE_MS    = 60 * 1_000 * 5;
	
	private final Class < T >      tClass;
	private final ObjectPool < T > objectPool;
	
	public ExceptionFactoryPool ( Class < T > tClass ) {
		this ( tClass, DEFAULT_POOL_PARTITION_SIZE, DEFAULT_POOL_MAX_SIZE, DEFAULT_POOL_MIN_SIZE, DEFAULT_POOL_MAX_IDLE_MS );
	}
	
	public ExceptionFactoryPool ( Class < T > tClass, int partition, int maxSize, int minSize, int maxIdleMilliseconds ) {
		this.tClass = tClass;
		objectPool = new ObjectPool <> ( setup ( partition, maxSize, minSize, maxIdleMilliseconds ), create ( ) );
	}
	
	private PoolConfig setup ( int partition, int maxSize, int minSize, int maxIdleMilliseconds ) {
		var configuration = new PoolConfig ( );
		configuration.setPartitionSize ( partition );
		configuration.setMaxSize ( maxSize );
		configuration.setMinSize ( minSize );
		configuration.setMaxIdleMilliseconds ( maxIdleMilliseconds );
		return configuration;
	}
	
	private ObjectFactory < T > create ( ) {
		return new ObjectFactory <> ( ) {
			@Override
			public T create ( ) {
				try {
					return tClass.getConstructor ( ).newInstance ( );
				} catch ( Exception e ) {
					return null;
				}
			}
			
			@Override
			public void destroy ( T object ) {
				// Handle object destruction, if necessary
			}
			
			@Override
			public boolean validate ( T object ) {
				return object != null;
			}
		};
	}
	
	public Poolable < T > getPool ( ) {
		try ( Poolable < T > poolable = objectPool.borrowObject ( ) ) {
			return poolable;
		}
	}
	
	public int getSize ( ) {
		return objectPool.getSize ( );
	}
	
	public void returnObject ( Poolable < ? extends Throwable > instance ) {
		objectPool.returnObject ( ( Poolable < T > ) instance );
	}
	
	@SneakyThrows
	public void shutdown ( ) {
		objectPool.shutdown ( );
	}
	
}
