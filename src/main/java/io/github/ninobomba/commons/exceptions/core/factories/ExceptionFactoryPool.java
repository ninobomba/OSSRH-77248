package io.github.ninobomba.commons.exceptions.core.factories;

import cn.danielw.fop.ObjectFactory;
import cn.danielw.fop.ObjectPool;
import cn.danielw.fop.PoolConfig;
import cn.danielw.fop.Poolable;
import lombok.SneakyThrows;

/**
 * The ExceptionFactoryPool class represents a pool of exception factories. It manages and provides access to exception factories for different types of exceptions.
 *
 * @param <T> the type of the throwable
 */
public final class ExceptionFactoryPool < T > {

	private static final int DEFAULT_POOL_PARTITION_SIZE = 2;
	private static final int DEFAULT_POOL_MIN_SIZE = 10;
	private static final int DEFAULT_POOL_MAX_SIZE = 15;
	private static final int DEFAULT_POOL_MAX_IDLE_MS = 60 * 1_000 * 5;

	private final Class < T > tClass;
	private final ObjectPool < T > objectPool;

	public ExceptionFactoryPool ( Class < T > tClass ) {
		this ( tClass, DEFAULT_POOL_PARTITION_SIZE, DEFAULT_POOL_MAX_SIZE, DEFAULT_POOL_MIN_SIZE, DEFAULT_POOL_MAX_IDLE_MS );
	}

	/**
	 * The ExceptionFactoryPool class represents a pool of exception factories. It manages and provides access to exception factories for different types of exceptions.
	 *
	 * @param tClass the type of the throwable
	 */
	public ExceptionFactoryPool ( Class < T > tClass, int partition, int maxSize, int minSize, int maxIdleMilliseconds ) {
		this.tClass = tClass;
		objectPool = new ObjectPool <> ( setup ( partition, maxSize, minSize, maxIdleMilliseconds ), create ( ) );
	}

	/**
	 * Sets up the configuration for the pool.
	 *
	 * @param partition           the number of partitions for the pool
	 * @param maxSize             the maximum size of the pool
	 * @param minSize             the minimum size of the pool
	 * @param maxIdleMilliseconds the maximum idle time for objects in the pool in milliseconds
	 * @return the configured PoolConfig object
	 */
	private PoolConfig setup ( int partition, int maxSize, int minSize, int maxIdleMilliseconds ) {
		var configuration = new PoolConfig ( );
		configuration.setPartitionSize ( partition );
		configuration.setMaxSize ( maxSize );
		configuration.setMinSize ( minSize );
		configuration.setMaxIdleMilliseconds ( maxIdleMilliseconds );
		return configuration;
	}

	/**
	 * Creates an object factory for the ExceptionFactoryPool.
	 *
	 * @return the object factory for the pool
	 */
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

	/**
	 * Retrieves a Poolable object from the pool.
	 *
	 * @return the poolable object retrieved from the pool
	 */
	public Poolable < T > getPool ( ) {
		try ( Poolable < T > poolable = objectPool.borrowObject ( ) ) {
			return poolable;
		}
	}

	/**
	 * Retrieves the size of the pool.
	 *
	 * @return the size of the pool
	 */
	public int getSize ( ) {
		return objectPool.getSize ( );
	}

	/**
	 * Returns a Poolable object back to the pool.
	 *
	 * @param instance the Poolable object to be returned
	 */
	public void returnObject ( Poolable < ? extends Throwable > instance ) {
		objectPool.returnObject ( ( Poolable < T > ) instance );
	}

	/**
	 * Shuts down the ExceptionFactoryPool by invoking the shutdown method on the underlying object pool.
	 */
	@SneakyThrows
	public void shutdown ( ) {
		objectPool.shutdown ( );
	}

}
