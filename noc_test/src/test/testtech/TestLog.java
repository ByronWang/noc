package testtech;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLog  extends TestCase{
	private Log log = LogFactory.getLog(TestLog.class);
	
	public void testA() {
		log.trace("trace");
		log.debug("Debug");
		log.info("Info");
		log.error("ERROR");
//		log.fatal("fatal");
	}
	
	public static void main(String[] args) {
		new TestLog().testA();
	}
}
