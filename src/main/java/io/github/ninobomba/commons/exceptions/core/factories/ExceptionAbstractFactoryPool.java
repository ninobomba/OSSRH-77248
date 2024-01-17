package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.project.IPackageUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class ExceptionAbstractFactoryPool {
	
	private static final String PACKAGE = "io.github.ninobomba.commons.exceptions.types";
	
	private static final ConcurrentHashMap < String, ExceptionFactoryPool < ? extends Throwable > > factoryMap = new ConcurrentHashMap <> ( );
	
	private static ExceptionAbstractFactoryPool exceptionAbstractFactoryPool;
	
	private ExceptionAbstractFactoryPool ( ) {
		init ( );
	}
	
	private void init ( ) {
		var classes = IPackageUtils.getCustomExceptionSet ( PACKAGE ).stream ( ).filter ( e -> !e.getClass ( ).equals ( RuntimeException.class ) ).toList ( );
		classes.forEach ( this::register );
	}
	
	public void register ( Class < ? extends Throwable > tClass ) {
		var factoryPool = new ExceptionFactoryPool <> ( tClass );
		factoryMap.putIfAbsent ( tClass.getSimpleName ( ), factoryPool );
	}
	
	public static ExceptionAbstractFactoryPool getInstance ( ) {
		if ( exceptionAbstractFactoryPool == null ) {
			exceptionAbstractFactoryPool = new ExceptionAbstractFactoryPool ( );
		}
		return exceptionAbstractFactoryPool;
	}
	
	public < T > ExceptionFactoryPool < ? extends Throwable > getFactory ( Class < T > tClass ) {
		return factoryMap.get ( tClass.getSimpleName ( ) );
	}
	
	public List < String > getFactoryNames ( ) {
		return factoryMap.keySet ( ).stream ( ).toList ( );
	}
	
	public void shutdown ( ) {
		factoryMap.forEach ( ( k, v ) -> v.shutdown ( ) );
	}
	
}
