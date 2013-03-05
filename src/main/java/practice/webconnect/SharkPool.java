package practice.webconnect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SharkPool {
	public static void main(String... args) {
		// Start Context
		ApplicationContext ac = new ClassPathXmlApplicationContext("practice/webconnect/shark-context.xml");
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ac.getBean("taskExecutor");
		for(int i = 0; i < 15; i++) {
			SharkThread shark = ac.getBean(SharkThread.class);
			shark.setRequestString("shark number" + i);
			//shark.setSleepTime(((i+2) % 7) * 1000);
			taskExecutor.execute(shark);
		}
		
		while(true) {
			int count = taskExecutor.getActiveCount();
			System.out.println("Active Sharks: " + count);
			if(count == 0) {
				taskExecutor.shutdown();
				System.out.println("Killed taskExecutor");
				break;
			}
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
