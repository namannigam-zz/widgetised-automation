package mobile.runner;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Driver {

    public static String module, testFile, device;
    static ArrayList<Test> tests;
    static HashMap<TestRunner, ArrayList> testMap = new HashMap<>();
    static String batchId = null;

    public static void main(String args[]) throws Exception {
        module = PropertyConstants.MODULE_NAME;
        testFile = System.getProperty(PropertyConstants.TEST_FILE_PROPERTY);
        if (System.getProperty(PropertyConstants.DEVICE_PROPERTY) != null)
            device = System.getProperty(PropertyConstants.DEVICE_PROPERTY);
        if (System.getProperty(PropertyConstants.BATCH_PROPERTY) != null &&
                !(System.getProperty(PropertyConstants.BATCH_PROPERTY).trim().isEmpty()))
            batchId = (System.getProperty(PropertyConstants.BATCH_PROPERTY));
        else
            batchId = UUID.randomUUID().toString();
        String jenkinsProjectName;
        String jenkinsBuildNum = batchId;
        if (batchId.contains("/")) {
            jenkinsProjectName = batchId.split("/")[0];
            jenkinsBuildNum = batchId.split("/")[1];
        } else {
            jenkinsProjectName = batchId;
        }
        File file = new File("./uuid.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(batchId);
        bw.close();
        Parser parser = Parser.getInstance();
        tests = parser.parse(testFile);
        overrideTestConfig(tests);
        createTestBucket(tests);
        startExecution(testMap);

        Test test = new Test();
        test.setName(testFile.replace(".xml", ""));

        String rawReportLocation =
                PropertyConstants.RAW_REPORT_LOCATION + (jenkinsProjectName != null ? jenkinsProjectName + "/" : "") +
                        jenkinsBuildNum + "/" + test.getName();
        String emailableReportLocation = PropertyConstants.EMAILABLE_REPORT_LOCATION +
                (jenkinsProjectName != null ? jenkinsProjectName + "/" : "") + jenkinsBuildNum + "/" + test.getName() +
                "/emailable-report.html";

        if (System.getProperty(PropertyConstants.DEVICE_PROPERTY) != null)
            System.setProperty(PropertyConstants.DEVICE_PROPERTY, device);
        if (StringUtils.isNotEmpty(System.getenv("JOB")) && System.getenv("JOB").equalsIgnoreCase("api_deployment")) {
            System.out.println("Test emailable report can be seen at " + emailableReportLocation);
            System.out.println("Test raw report can be seen at " + rawReportLocation);
        } else {
            System.out.println("Test report can be seen at " + rawReportLocation);
            System.out.println("Test emailable report can be seen at " + emailableReportLocation);
        }

        if ((TestRunner.numberOfFailedTests > 0 || TestRunner.numberOfSkippedTests > 0)) {
            System.exit(1);
        }
    }

    static void createTestBucket(ArrayList<Test> tests) {
        TestRunner tr = new TestRunner();
        testMap.put(tr, tests);
    }

    static void startExecution(final HashMap<TestRunner, ArrayList> testMap) {
        for (Map.Entry<TestRunner, ArrayList> entry : testMap.entrySet()) {
            final TestRunner tr = entry.getKey();
            final ArrayList<Test> tList = entry.getValue();
            try {
                for (Test t : tList) {
                    tr.execute(t, batchId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void overrideTestConfig(ArrayList<Test> tests) throws IOException {
        String testConfigOverride = null;
        for (Test t : tests) {
            if (t.getName().toLowerCase().contains(PropertyConstants.ANDROID_PROPERTY))
                testConfigOverride = System.getenv("androidTestXml");
            else if (t.getName().toUpperCase().contains(PropertyConstants.IOS_PROPERTY))
                testConfigOverride = System.getenv("iosTestXml");
            if (testConfigOverride != null) {
                File apiCongifFile = new File(t.getPath());
                FileOutputStream fooStream = new FileOutputStream(apiCongifFile, false);
                byte[] myBytes = testConfigOverride.getBytes();
                fooStream.write(myBytes);
                fooStream.close();
            }
        }
    }
}