package mobile.appium;

import mobile.base.CommandPrompt;

public class AppiumManager {
    CommandPrompt commandPrompt = new CommandPrompt();
    AvailablePort availablePort = new AvailablePort();

    /**
     * Starts Appium with auto generated appium port
     *
     * @param platform the platform of device
     * @return port number to run the serve on
     * @throws InterruptedException
     */
    public String startAppium(String platform) throws InterruptedException {
        System.out.println("Starting Appium Server..");
        String port = availablePort.getPort();
        String mobileOS = "";
        String nativelib = "";
        commandPrompt.killAppiumProcess(platform);
        if (platform.equalsIgnoreCase("Android")) {
            port = "4723";
            mobileOS = "Android";

        } else if (platform.equalsIgnoreCase("IOS")) {
            port = "4724";
            mobileOS = "iOS";
            nativelib = " --native-instruments-lib";
        }
        String command =
                "/Applications/Appium.app/Contents/Resources/node/bin/node /Applications/Appium.app/Contents/Resources/node_modules/appium/lib/server/main.js --command-timeout 120 --session-override" +
                        nativelib + " --platform-name " + mobileOS + " --log-timestamp -a 0.0.0.0 -p " +
                        port; //+" --chromedriver-port "+chromePort+" -bp "+bootstrapPort;
        System.out.println(command);
        String output = commandPrompt.runCommand(command);
        return port;
    }

    /**
     * Stops appium server destroying the process
     */
    public void stopAppium() {
        commandPrompt.stopCommand();
    }
}