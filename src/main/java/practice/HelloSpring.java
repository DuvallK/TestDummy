package practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class HelloSpring {
	// Add logger for debug purposes
	private final static Logger logger = LoggerFactory.getLogger(HelloSpring.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Spring 3.0 Concept testing");
		logger.info("Starting Spring context...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("practice/applicationContext.xml");
		printBreak(false);
		System.out.println("Testing Prototype Scope");
		JumpingBean jb = getJumpingBean(context);
		jb.setJumpDistance(12);
		jb.printData();
		
		getJumpingBean(context).printData();
		printBreak(false);
		logger.info("Grabbing KidneyBean...");
		((Legume)context.getBean("primaryLegume")).printData();
		
		getJumpingBean(context).printData();
		
		printBreak(false);
		System.out.println("Testing thread pooling");
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
		for(int i = 0; i < 5; i++) {
			SilkThread thread = (SilkThread) context.getBean(SilkThread.class);
			thread.setBean("worm" + i);
			thread.setSleepTime(i * 1000);
			taskExecutor.execute(thread);
		}
		
		SilkThread thread = context.getBean(SilkThread.class);
		thread.setBean("sandworm");
		thread.setSleepTime(2000);
		try {
			taskExecutor.execute(thread);
		} catch (Exception e) {
			// can't catch it!
			System.out.println("Ha! Caught you! " + e);
		}
		
		while(true) {
			int count = taskExecutor.getActiveCount();
			System.out.println("Active SilkThreads: " + count);
			if(count == 0) {
				taskExecutor.shutdown();
				break;
			}
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// cleanup time
		// Testing cleanup of prototype beans
		printBreak(false);
		jb = null;
		System.gc();	// won't actually call the jumping beans' @PreDestroy methods
		
		System.out.println("All done, pack it in");
		logger.info("Closing Spring context...");
		context.close();
	}
	
	
	
	private static JumpingBean getJumpingBean(ClassPathXmlApplicationContext context) {
		logger.info("Grabbing JumpingBean instance...");
		JumpingBean jb = (JumpingBean)context.getBean(JumpingBean.class);
		return jb;
	}
	
	/**
	 * Prints a series of hyphens for use as a section break
	 * @param stdOut specifies whether to print to stdout (true)
	 * 	or to print to log (false)
	 */
	private static void printBreak(boolean stdOut) {
		if(stdOut)
			System.out.println("--------------------------------");
		else
			logger.info("--------------------------------");
	}

}
