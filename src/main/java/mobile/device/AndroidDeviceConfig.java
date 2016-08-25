package mobile.device;

import mobile.base.CommandPrompt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AndroidDeviceConfig {

    CommandPrompt commandPrompt = new CommandPrompt();
    Map<String, String> adbDevices = new HashMap<>();

    /**
     * Starts adb server via terminal commands
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void startADB() throws IOException, InterruptedException {
        String output = commandPrompt.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines.length == 1)
            System.out.println("adb service already started");
        else if (lines[1].equalsIgnoreCase("* daemon started successfully *"))
            System.out.println("adb service started");
        else if (lines[0].contains("internal or external command")) {
            System.out.println("adb path not set in system varibale");
            System.exit(0);
        }
    }

    /**
     * Stops the adb server running in background
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void stopADB() throws IOException, InterruptedException {
        commandPrompt.runCommand("adb kill-server");
    }

    /**
     * Gets the list of devices from the command prompt result
     *
     * @return map of device
     * @throws IOException
     * @throws InterruptedException
     */
    public Map<String, String> getAndroidDeviceList() throws IOException, InterruptedException {
        int index = 0;
        startADB();
        String output = commandPrompt.runCommand("adb devices");
        String[] lines = output.split("\n");

        if (lines.length <= 1) {
            System.out.println("No Device Connected");
            stopADB();
            System.exit(0);    // exit if no connected devices found
        }

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].contains("device")) {
                String deviceID = lines[i].replaceAll("\\t", "").replaceAll("device", "").replaceAll("\\s+$", "");
                adbDevices.put("deviceID" + i, deviceID);
                String model = commandPrompt.runCommand("adb -s " + deviceID + " shell getprop ro.product.model").replaceAll("\\s+", "");
                String brand = commandPrompt.runCommand("adb -s " + deviceID + " shell getprop ro.product.brand").replaceAll("\\s+", "");
                String osVer = commandPrompt.runCommand("adb -s " + deviceID + " shell getprop ro.build.version.release").replaceAll("\\s+", "");
                String deviceName = brand + " " + model;
                adbDevices.put("deviceName" + i, deviceName);
                adbDevices.put("osVersion" + i, osVer);
                System.out.println(deviceName + "with" + deviceID + "and" + osVer + " is connected.");
            } else if (lines[i].contains("unauthorized")) {
                lines[i] = lines[i].replaceAll("unauthorized", "");
                String deviceID = lines[i];
                System.out.println(deviceID + " is unauthorized.");
            } else if (lines[i].contains("offline")) {
                lines[i] = lines[i].replaceAll("offline", "");
                String deviceID = lines[i];
                System.out.println(deviceID + " is offline.");
            }
        }
        return adbDevices;
    }
}
