package ipreomobile.templates.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import ipreomobile.core.Driver;
import ipreomobile.core.TestListener;

@Listeners(TestListener.class)
public class StartupTest extends EmptyTest {
	
    @BeforeMethod
    public void testCaseSetup(){
        Driver.init();
        Driver.startApplication();
    }

}
