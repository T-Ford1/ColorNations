package net;

import components.gui.MessageBar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author ford.terrell
 */
public class Network extends Thread {

    public static IOGate io;
    public static String name;
    public static int id;

    public Network() {
        try {
            tryConnect();
        } catch (Exception ex) {
        }
    }

    public static final void tryConnect() throws Exception {
        io = new IOGate();
        if(!io.isConnected()) {
            return;
        }
        try {
            Scanner keyb = new Scanner(new File("res/net/client.dat"));
            id = Integer.parseInt(keyb.nextLine());
            name = keyb.nextLine();
        } catch (FileNotFoundException ex) {
            id = -1;
            name = JOptionPane.showInputDialog("What is your name?", "Input name");
        }
        io.output(id + "");
        io.output(name);
        if (id == -1) {
            id = Integer.parseInt(io.getInput());
        }
        try (PrintWriter pr = new PrintWriter("res/net/client.dat")) {
            pr.println(id);
            pr.print(name);
        }
    }

    public void run() {
        while (io.isConnected()) {
            try {
                parseInput(io.getInput());
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void parseInput(String in) {
        if (in.startsWith("0")) {
            MessageBar.addMessage(0, in.substring(2));
        } else if (in.startsWith("1")) {
            parseCommand(in.split(" "));
        }
    }

    private static void parseCommand(String[] cmd) {
        switch (cmd[1]) {
            case "0":
                close();
                break;
        }
    }

    public static void close() {
        io.close();
    }
}
