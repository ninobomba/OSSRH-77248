package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.exceptions.commons.ExceptionsConstants;
import io.github.ninobomba.commons.project.IPackageUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a pool of exception factories. It is responsible for managing and providing access to exception factories for different types of exceptions.
 */
public final class ExceptionAbstractFactoryPool {
	
	private static final String PACKAGE = ExceptionsConstants.CUSTOM_PACKAGE_NAME.getValue ();
	
	private static final ConcurrentHashMap < String, ExceptionFactoryPool < ? extends Throwable > > factoryMap = new ConcurrentHashMap <> ( );
	
	private static ExceptionAbstractFactoryPool exceptionAbstractFactoryPool;
	
	private ExceptionAbstractFactoryPool ( ) {
		init ( );
	}
	
	private void init ( ) {
		var classes = Objects
				.requireNonNull ( IPackageUtils.findAllClassesUsingReflections ( PACKAGE ) )
				.stream ( ).toList ( );
		classes.forEach ( this::register );
	}
	
	/**
	 * Registers the given throwable class in the exception factory pool.
	 *
	 * @param tClass the class of the throwable to register
	 */
	public void register ( Class < ? extends Throwable > tClass ) {
		var factoryPool = new ExceptionFactoryPool <> ( tClass );
		factoryMap.putIfAbsent ( tClass.getSimpleName ( ), factoryPool );
	}
	
	/**
	 * Returns the singleton instance of ExceptionAbstractFactoryPool. If the instance has not been created yet, it creates a new instance and returns it.
	 *
	 * @return the singleton instance of ExceptionAbstractFactoryPool
	 */
	public static synchronized ExceptionAbstractFactoryPool getInstance ( ) {
		if ( exceptionAbstractFactoryPool == null ) {
			exceptionAbstractFactoryPool = new ExceptionAbstractFactoryPool ( );
		}
		return exceptionAbstractFactoryPool;
	}
	
	/**
	 * Retrieves the exception factory associated with the given throwable class.
	 *
	 * @param tClass the class of the throwable to get the factory for
	 * @param <T>    the type of the throwable
	 * @return the exception factory associated with the given throwable class, or null if not found
	 */
	public < T > ExceptionFactoryPool < ? extends Throwable > getFactory ( Class < T > tClass ) {
		return factoryMap.get ( tClass.getSimpleName ( ) );
	}
	
	/**
	 * Retrieves a list of factory names from the exception factory pool.
	 *
	 * @return a list of factory names
	 */
	public List < String > getFactoryNames ( ) {
		return factoryMap.keySet ( ).stream ( ).toList ( );
	}
	
	/**
	 * Shuts down the exception factory pool by invoking the shutdown method on each factory in the pool.
	 */
	public void shutdown ( ) {
		factoryMap.forEach ( ( k, v ) -> v.shutdown ( ) );
	}
	
}
