package org.cloudy.ddl.rule;

import java.util.Map;

import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;

public abstract class CCDLFunction implements AviatorFunction {
	
	public abstract String getName();
	
	public abstract AviatorObject call(Map<String, Object> env, AviatorObject... args);
}
