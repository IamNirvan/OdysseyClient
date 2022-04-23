package com.odysseyclient.util;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientConfiguration {

    // TODO: Find a way to use a try with resource statement here
    public static String getHostName() {
        Scanner SCANNER = new Scanner(System.in);
        ConsoleWriter.writeMessageToConsole("Please enter the server name: ");
        return SCANNER.next();
    }

    // TODO: Find a way to use a try with resource statement here
    public static int getServerPortNumber() {
        Scanner SCANNER = new Scanner(System.in);
        ConsoleWriter.writeMessageToConsole("Please enter the server's port number: ");
        return SCANNER.nextInt();
    }

    /**
     * Responsible for creating a new socket for the specified port number.
     * It will continue to prompt the user to enter a port number and server name
     * if it fails to create a socket using those parameters.
     * @return A socket, if it was successfully created. Otherwise, null.
     * */
    public static Socket createClientSocket() {
        Socket socket = null;
        do {
            String hostName = getHostName();
            int port = getServerPortNumber();
            try {
                socket = new Socket(hostName, port);
            } catch (IOException e) {
                ConsoleWriter.writeErrorToConsole("Failed to create a socket.", e.toString());
            }
        } while(socket == null);
        return socket;
    }

    public static String getUserName() {
        Scanner SCANNER = new Scanner(System.in);
        ConsoleWriter.writeMessageToConsole("Please enter your username: ");
        return SCANNER.nextLine();
    }
}
