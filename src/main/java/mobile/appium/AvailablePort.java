package mobile.appium;

import java.io.IOException;
import java.net.ServerSocket;

public class AvailablePort {

    /***
     * Method to get the available ports on a machine
     *
     * @return the port value as a string
     */
    public String getPort() {
        try {
            ServerSocket socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            String port = Integer.toString(socket.getLocalPort());
            socket.close();
            return port;
        } catch (IOException except) {
            except.printStackTrace();
            return null;
        }
    }
}