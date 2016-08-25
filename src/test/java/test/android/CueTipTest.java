package test.android;

import mobile.util.Common;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * Created by naman.nigam on 11/03/16.
 */
public class CueTipTest extends BaseTest {

    Common common;

    @BeforeTest
    public void setCheckoutPageActions() {
        common = new Common();
        String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
        System.out.println("************************ STARTING  :: " + className[className.length - 1] + " ***********************");
    }


    @AfterTest
    public void testEndConsole() {
        String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
        System.out.println("************************ COMPLETED :: " + className[className.length - 1] + " ***********************");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        common.captureScreenshot(result);
        if (result.getStatus() == ITestResult.FAILURE) {
            common.captureDump();
        }
    }
}
