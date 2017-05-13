package com.bj58.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *Created by fei.gao on 2017/5/11.
 * 方法(服务降级) 管理
 */
public class ServiceStatusManager implements ServiceDegradationInf {

	/**
	 * 单例模式
	 */
	private static final ServiceStatusManager instance = new ServiceStatusManager();

	/**
	 * 说明： 1 					-- 表示该接口进行降级，服务返回默认值
	 *        method不存在，或 0 -- 表示服务不进行降级
	 *
	 *  	map<mehtod,status>
	 *  	map<com.bj58.test.TestServiceImpl.getBeans(java.lang.String,int,java.lang.Integer),1>
	 *
	 * */
	private final ConcurrentHashMap<String, Integer> methodStatusMap = new ConcurrentHashMap<String,Integer>();
	
	private ServiceStatusManager(){}
	
	public static ServiceStatusManager getInstance(){
		return ServiceStatusManager.instance;
	}
	
	public  Map<String,Object> getSnapShot(){
		return new HashMap<String,Object>(methodStatusMap);
	}

	/**
	 * 将方法置为open ,<method,1>  进行降级接口
	 * @param method
	 * @return
	 */
	@Override
	public boolean openService(String method){
		return methodStatusMap.put(method, 1) != null;
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	@Override
	public boolean closeService(String method){
		return methodStatusMap.remove(method) != null;
	}

	@Override
	public boolean isOpenService(String method){
		/*Set<String> set = methodStatusMap.keySet();
		for(Iterator<String> it = set.iterator(); it.hasNext();){
			String key = it.next();
			System.out.println(key+" "+methodStatusMap.get(key));
		}*/
		return methodStatusMap.containsKey(method) && (methodStatusMap.get(method).intValue() == 1);
	}
}
