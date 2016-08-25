package mobile.runner;

import mobile.base.SysConstants;
import mobile.listener.CustomAnnotationTransformer;
import mobile.listener.TestExecutionListener;
import mobile.listener.TestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static int numberOfFailedTests = 0;
    public static int numberOfSkippedTests = 0;
    private String testReport;

    public TestRunner() {
        this.testReport = SysConstants.Config.TESTS_REPORT;
    }

    public void execute(Test test, String batchId) {
        try {
            String outputDir = testReport + "/" + batchId + "/" + test.getName();
            System.out.println("Check the test report at " + outputDir);
            TestListener testListener = new TestListener();
            TestExecutionListener testExecutionListener = new TestExecutionListener();
            ArrayList<String> suiteFiles = new ArrayList<>();
            suiteFiles.add(test.getPath());
            XmlSuite xmlSuite = new XmlSuite();
            xmlSuite.setSuiteFiles(suiteFiles);
            TestNG testNG = new TestNG();
            testNG.addListener(testListener);
            testNG.addListener(testExecutionListener);
            testNG.setCommandLineSuite(xmlSuite);
            testNG.setOutputDirectory(outputDir);
            testNG.setAnnotationTransformer(new CustomAnnotationTransformer());
            testNG.run();
            List<ITestResult> failed = testListener.getFailedTests();
            List<ITestResult> passed = testListener.getPassedTests();
            List<ITestResult> skipped = testListener.getSkippedTests();
            test.setPassed(passed.size());
            test.setFailed(failed.size());
            test.setSkipped(skipped.size());
            numberOfFailedTests = test.getFailed();
            numberOfSkippedTests = test.getSkipped();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}