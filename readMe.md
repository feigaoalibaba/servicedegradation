
java 服务屏蔽开关系统，可以手工降级服务。关闭服务 基于spring AOP机制，可以在特殊情况下屏蔽相关service类的某些返回，并且支持定义默认返回结果，随机屏蔽某些异常服务。 通过启动一个内置的http server来监听外部指令。

使用指南：
1.在spring配置文件中添加如下，其中switch-service-pointcut是添加紧急情况下需要屏蔽的方法列表
<aop:config proxy-target-class="true"></aop:config>

<bean id="switchInteceptor" class="com.bj58.interceptor.SwitchInteceptor">
    </bean>
    <bean id="switch-service-pointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut">
        <property name="patterns">
            <list>
                <value>com.bj58.test.*</value>
            </list>
        </property>
    </bean>
    <aop:config>
        <aop:advisor advice-ref="switchInteceptor" pointcut-ref="switch-service-pointcut"/>
    </aop:config>
    
    <bean id="switchControlHttpServer" class="com.bj58.server.SwitchControlHttpServer" init-method="init"></bean>
    
    <bean id="testService" class="com.bj58.test.TestServiceImpl" />
    
    <bean id="testController" class="com.bj58.test.TestController" />
</bean>

2.例如下面的service，上面注释分别是在应用启动后手工屏蔽该服务调用，以后每次调用直接用参数的jsonResult反序列后返回， classmethod是具体到某个方法名称，status为open关闭该服务，close表示重新打开服务，jsonResult是mock返回结果的json串， 如果是基本类型，则必须用ret作为key，其他list，bean之类的就直接用json串，type表示如果list有泛型的话则是返回的类完整类型;

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
3.调用示例代码
public class MainTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-bean.xml");
		TestService testControl = (TestService) context.getBean("testService");
		try{	
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
	}
}


这里只是提供一种示例，如果要在生产环境中使用，则需要对并发控制，返回结果的序列化等各种情况进行控制， 同时还需要对权限，后台管理系统等可以做优化。
