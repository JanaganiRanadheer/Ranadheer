package driverfactory;

import org.testng.annotations.Test;

public class AppTest {
@Test
public void kickstart() throws Throwable {
	DriverScript ds = new DriverScript();
	ds.starttest();
}
}
