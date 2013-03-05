package practice;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SilkThread extends Thread {
	
	private String bean;
	private int sleepTime;
	
	public String getBean() {
		return bean;
	}



	public void setBean(String bean) {
		this.bean = bean;
	}

	public int getSleepTime() {
		return sleepTime;
	}



	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		System.out.println(bean + " is starting.");
		
		if(bean.equals("sandworm"))
			throw new RuntimeException("Kaboom! Dune dies!");
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(bean + " is finishing");
	}
}
