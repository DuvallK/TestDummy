package practice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="primaryLegume")
public class LegumeImpl implements Legume {
	@Value(value = "kidney")
	private String classification;
	
	private static final Logger logger = LoggerFactory.getLogger(LegumeImpl.class);

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	@Override
	public void printData() {
		System.out.println("I am a legume:" + classification);
	}
	
	@PostConstruct
	public void afterBirth() {
		logger.info("KidneyBean " + this.hashCode() + " created.");
	}
	
	@PreDestroy
	public void beforeDeath() {
		logger.info("KidneyBean " + this.hashCode() + " destroyed.");
	}
}
