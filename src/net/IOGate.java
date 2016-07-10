package net;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author ford.terrell
 */
public class IOGate {

    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static boolean connected;

    public IOGate() {
        try {
            socket = new Socket(java.net.InetAddress.getLocalHost().getHostAddress(), 9001);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            connected = false;
        }
    }

    public String getInput() {
        if (connected) {
            try {
                return in.readLine();
            } catch (IOException ex) {
                connected = false;
            }
        }

        return null;
    }

    public void output(String output) {
        if (connected) {
            try {
                out.println(output);
            } catch (Exception ex) {
                connected = false;
            }
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void close() {
        if (connected) {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
    }
}
