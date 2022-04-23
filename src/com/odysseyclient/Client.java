package com.odysseyclient;

import com.odysseyclient.util.Broadcaster;
import com.odysseyclient.util.ConsoleWriter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// TODO: notify the client's client handler when it is about to leave, so the client handler can also close its socket or something...

public class Client {
    private final Socket SOCKET;
    private BufferedWriter buffWriter;
    private BufferedReader buffReader;
    private final String USER_NAME;

    public Client(Socket SOCKET, String username) {
        this.SOCKET = SOCKET;
        this.USER_NAME = username;
        setUpClient();
    }

    /**
     * Initializes the client's BufferedWriter, BufferedReader and sends the user name.
     * */
    private void setUpClient() {
        try {
            this.buffWriter = new BufferedWriter(new OutputStreamWriter(this.SOCKET.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(this.SOCKET.getInputStream()));
            Broadcaster.broadcastUserName(this.buffWriter, this.USER_NAME);
        } catch (IOException e) {
            ConsoleWriter.writeErrorToConsole("A problem occurred when setting up the client", e.toString());
            expelResources();
        }
    }

    /**
     * Gets the message from the user and sends it to its ClientHandler in the server.
     * */
    public void sendMessage() {
        try(Scanner SCANNER = new Scanner(System.in)) {
            while(this.SOCKET.isConnected()) {
                ConsoleWriter.printInputString("Enter your message");
                Broadcaster.broadcastStandardMessage(this.buffWriter, SCANNER.nextLine(),
                    this.USER_NAME);
            }
        } catch (IOException e) {
            ConsoleWriter.writeErrorToConsole("A problem occurred when sending a message", e.toString());
            expelResources();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                while(SOCKET.isConnected()) {
                    ConsoleWriter.writeMessageToConsole(buffReader.readLine());
                    ConsoleWriter.printInputString("Enter your message");
                }
            } catch (IOException e) {
                e.printStackTrace();
                expelResources();
            }
        }, "ListenerThread").start();
    }

    public void expelResources() {
        try {
            if(this.buffWriter != null) {
                this.buffWriter.close();
            }
            if(this.buffReader != null) {
                this.buffReader.close();
            }
            if(this.SOCKET != null) {
                this.SOCKET.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
