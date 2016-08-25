package mobile.runner;

import java.io.*;
import java.util.ArrayList;

public class Parser {

    private static Parser instance = null;
    ArrayList<Test> testList = new ArrayList<>();

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public ArrayList<Test> parse(String testFile) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/" + testFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            File tempFile = new File(testFile);
            tempFile.createNewFile();
            FileWriter fileWriter = new FileWriter(tempFile.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
            }
            bufferedWriter.close();
            Test test = new Test();
            test.setName(testFile.replace(".xml", ""));
            test.setPath(tempFile.getAbsolutePath());
            if (System.getProperty("testFile").equalsIgnoreCase("ui-test.xml")) {
                test.setModule("Mobile-Apps");
                test.setFeature("Apps");
                test.setType("MobileApp");
                test.setSubFeature(System.getProperty("platform"));
            }
            testList.add(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }
}