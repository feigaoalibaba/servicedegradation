package com.bj58.test;

import java.util.List;

public interface TestService {
	
	
	public void hello();
	
	
	public String sayHello();
	
	public List<TestBean> getNames();
	
	public TestBean getBeans(String name, int a, Integer m);

}
