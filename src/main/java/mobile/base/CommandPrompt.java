package mobile.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandPrompt {
    Process process;
    ProcessBuilder builder = new ProcessBuilder();

    /**
     * Execute terminal commands on windows or mac according to the OS
     *
     * @param command to run
     */
    public String runCommand(String command) {
        try {
            String os = System.getProperty("os.name");
            String job_name = System.getProperty("JOB_NAME");
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String date_time = dateFormat.format(date);
            // for Windows
            if (os.contains(SysConstants.OS_NAME_WINDOWS)) {
                builder = new ProcessBuilder("cmd.exe", "/c", command);
                builder.redirectErrorStream(true);
                Thread.sleep(1000);
                process = builder.start();
            }
            //for Mac
            else {
                String[] data = command.split(" ");
                List<String> commandList = Arrays.asList(data);
                builder.command(commandList);
                builder.redirectOutput(new File("/var/lib/jenkins/jobs/" + job_name + "/appium-" + date_time + ".log"));
                process = builder.start();
            }

            process.waitFor(10000, TimeUnit.MILLISECONDS);
            if (process.isAlive()) {
                System.out.println("Appium Server started!");
            } else {
                System.out.println("Appium Server not started!");
                System.exit(0);
            }
            return "Process Started";
        } catch (InterruptedException | IOException except) {
            except.printStackTrace();
            return null;
        }
    }

    /**
     * Destroys the process started via runCommand() method
     */
    public void stopCommand() {
        process.destroy();
    }

    /**
     * Kills the appium process that is already running,
     * so that script can start a fresh appium process
     *
     * @param platformType on which device platform the execution would continue
     */
    public void killAppiumProcess(String platformType) {
        System.out.println("Checking for any existing Appium Process");
        List<String> commandList = new ArrayList<>();
        commandList.add("sh");
        commandList.add("-c");
        commandList.add("ps -ax | grep node | grep appium | grep -i " + platformType);
        ProcessBuilder killbuilder = new ProcessBuilder();
        Process killproc;
        try {
            killbuilder.command(commandList);
            killproc = killbuilder.start();
            killproc.waitFor(200, TimeUnit.MILLISECONDS);
            BufferedReader r = new BufferedReader(new InputStreamReader(killproc.getInputStream()));
            String line;
            String proc_id = "";
            while ((line = r.readLine()) != null) {
                if (line.contains("main.js")) {
                    proc_id = line.split(" ")[0];
                    break;
                }
            }
            if (!proc_id.equals("")) {
                System.out.println("Appium process already running with process id " + proc_id + ". Killing it");
                commandList.clear();
                commandList.add("sh");
                commandList.add("-c");
                commandList.add("kill -9 " + proc_id);
                killbuilder.command(commandList);
                killproc = killbuilder.start();
                killproc.waitFor(200, TimeUnit.MILLISECONDS);
                r = new BufferedReader(new InputStreamReader(killproc.getInputStream()));
                Boolean proc_killed = true;
                while ((line = r.readLine()) != null) {
                    if (line.contains("No such process")) {
                        proc_killed = false;
                    }
                }
                if (proc_killed) {
                    System.out.println("Appium process could not be killed");
                } else {
                    System.out.println("Appium process killed");
                }
            }
        } catch (InterruptedException | IOException except) {
            except.printStackTrace();
            System.out.println("Process not killed");
        }
    }
}