package com.bj58.test;

import java.util.ArrayList;
import java.util.List;

public class TestServiceImpl implements TestService{
	
	//http://localhost:8080/control/a.htm?classmethod=com.bj58.test.TestServiceImpl.hello()&status=open&jsonResult=1
	public void hello(){
		System.out.println("hello");
	}
	
	//http://localhost:8080/control/a.htm?classmethod=com.bj58.test.TestServiceImpl.sayHello()&status=open&jsonResult={ret:%22goodbuy%22}
	public String sayHello(){
		return "sayHello";
	}
	//http://localhost:8080/control/a.htm?classmethod=com.bj58.test.TestServiceImpl.getNames()&status=open&jsonResult=[{"catList":[],"id":1,"name":"aaa"},{"catList":[],"id":1,"name":"aaa"},{"catList":[],"id":1,"name":"aaa"}]&type=com.bj58.test.TestBean
	public List<TestBean> getNames(){
		return null;
	}
	
//	http://localhost:8080/control/a.htm?classmethod=com.bj58.test.TestServiceImpl.getBeans(java.lang.String,int,java.lang.Integer)&status=open&jsonResult={%22catList%22:[%22123%22,%22456%22,%22789%22],%22id%22:1,%22name%22:%22aaa%22}
	public TestBean getBeans(String name,int a,Integer m1){
		TestBean bean = new TestBean();
		bean.setId(22);
		bean.setName("gaoei");
		List list = new ArrayList<String>();
		list.add("ddd");
		bean.setCatList(list);
		return bean;
	}

}
