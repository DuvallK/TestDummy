package practice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class JumpingBean {
	private final static Logger logger = LoggerFactory.getLogger(JumpingBean.class);
	
	@Value(value="5")
	private int jumpDistance;
	
	@Autowired
	private LegumeImpl legume;

	public int getJumpDistance() {
		return jumpDistance;
	}

	public void setJumpDistance(int jumpDistance) {
		this.jumpDistance = jumpDistance;
	}
	
	public Legume getLegume() {
		return legume;
	}
	
	public void printData() {
		System.out.println("This jumping bean " + hashCode() + " jumps " + jumpDistance + " feet and talks to a bean called "
			+ legume.getClassification());
	}
	
	@PostConstruct
	public void readyToJump() {
		logger.info("JumpingBean " + this.hashCode() + " created with KidneyBean " + legume.hashCode());
	}
	
	// Apparently, without creating a custom CommonAnnotationBeanPostProcessor, the @PreDestroy method
	// will never be called on prototype beans. So be careful about resource allocation with these!
	@PreDestroy
	public void fallingToDeath() {
		// In this particular case, KidneyBean is a singleton bean anyway, so we don't have to kill it
		logger.info("JumpingBean " + this.hashCode() + " dying with KidneyBean " + legume.hashCode());
	}
}
