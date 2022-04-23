package com.odysseyclient;

import com.odysseyclient.util.ClientConfiguration;

import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        Socket socket = ClientConfiguration.createClientSocket();
        String userName = ClientConfiguration.getUserName();
        Client client = new Client(socket, userName);

        client.listenForMessage();
        client.sendMessage();
    }
}
