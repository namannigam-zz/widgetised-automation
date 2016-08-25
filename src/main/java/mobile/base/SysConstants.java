package mobile.base;

/**
 * Created by naman.nigam on 03/07/16.
 */
public class SysConstants {

    public static final String OS_NAME_WINDOWS = "Windows";

    public class DeviceConstants {
        public static final String ANDROID_DEVICE_NAME = "TestDevice";
        public static final String IOS_DEVICE_NAME = "iPhone 5s";
    }

    public class SetUpConstants {
        public static final String REMOTE_MACHINE_NAME = "MacMini";
        public static final String LOCAL_MACHINE_NAME = "Local";
        public static final String HUB_URL = "http://0.0.0.0:4723/wd/hub";
    }

    public class Config {
        public static final String TEST_HOME = "./automobile";
        public static final String TESTS_REPORT = "report";

        public static final String DB_TYPE = "jdbc:mysql://";
        public static final String DB_HOST_NAME = "mongo-db";
        public static final String DB_PORT = "3306";
        public static final String DB_NAME = "automation";

        public static final String USER = "qa";
        public static final String PASSWORD = "aut0m@ti0n";
    }

}