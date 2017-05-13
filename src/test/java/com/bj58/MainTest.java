package com.bj58;

import java.util.List;


import com.bj58.test.TestBean;
import com.bj58.test.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-bean.xml");
		TestService testControl = (TestService) context.getBean("testService");
		try{
//			
			while(true){
			
				System.out.println(testControl.sayHello());
				
				
				/*TestBean bean = testControl.getBeans("a",1,new Integer(5));
				if(bean != null)
				System.out.println(bean.getId() + bean.getName() + bean.getCatList());
				if(null != bean.getBean()){
					System.out.println(bean.getBean().getName());
				}*/
				
				Thread.sleep(5000);
			}
			/*List<TestBean> list = testControl.getNames();
			for(TestBean bean: list){
				System.out.println(bean.getId() + bean.getName() + bean.getCatList());
			}
			TestBean bean = testControl.getBeans();
			System.out.println(bean.getId() + bean.getName() + bean.getCatList());*/
		}catch(Exception e){
			e.printStackTrace();
		}

		
	/*	for(int i = 0; i < 10; i++){
			try{
//				testControl.hello();
//				System.out.println(testControl.sayHello());
				List<TestBean> list = testControl.getNames();
				for(TestBean bean: list){
					System.out.println(bean.getId() + bean.getName() + bean.getCatList());
				}
//				TestBean bean = testControl.getBeans();
//				System.out.println(bean.getId() + bean.getName() + bean.getCatList());
			}catch(Exception e){
				e.printStackTrace();
			}	
		}*/

	}
}
