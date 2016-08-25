package mobile.listener;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestExecutionListener implements IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        System.out.println(iInvokedMethod.getTestMethod().getMethodName() + " -> -> " + ITestResult.FAILURE);
    }

	@Override
	public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(iInvokedMethod.getTestMethod().getMethodName() + " -> -> " + ITestResult.FAILURE);
            System.out.println("-------------------------------------------------------------------------------");
        } else {
            System.out.println(iInvokedMethod.getTestMethod().getMethodName() + " -> -> " + ITestResult.SUCCESS);
            System.out.println("-------------------------------------------------------------------------------");
        }
	}
}
