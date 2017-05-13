package com.bj58.interceptor;

import com.bj58.factory.ObjectProductionFactory;
import com.bj58.manager.ServiceStatusManager;
import com.google.common.base.Joiner;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 *  Created by fei.gao on 2017/5/11.
 *  方法拦截
 */
public class SwitchInteceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		
		String classMethod = this.getClassMethod(method);
		System.out.println(classMethod);
		
		if(ServiceStatusManager.getInstance().isOpenService(classMethod)){
			Class<?> retClass = method.getReturnType();
			if(retClass.getName().equals("void")){
				return 1;
			}
			Object obj = ObjectProductionFactory.getRetInstance(classMethod, retClass);
			return obj;
		}
		return invocation.proceed();
	}

	/**
	 * 获取方法的全称
	 * @param method
	 * @return
	 */
	public String getClassMethod(Method method){
		
		StringBuffer classMethod = new StringBuffer(); 
		classMethod.append(method.getDeclaringClass().getName() + "." + method.getName()).append("(");
		
		 Class<?>[] parameterTypes = method.getParameterTypes();
		 
		 String [] parameterStr = new String[parameterTypes.length];
		 for(int i=0 ; i< parameterTypes.length; i++){
			 parameterStr[i] = parameterTypes[i].getName();
		 }
		 
		 classMethod.append(Joiner.on(",").skipNulls().join(parameterStr));
		 classMethod.append(")");
		
		 return classMethod.toString();
	}
}
