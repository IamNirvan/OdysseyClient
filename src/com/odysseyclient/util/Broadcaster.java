package com.odysseyclient.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.MessageFormat;

public final class Broadcaster {
    public static void broadcastStandardMessage(BufferedWriter bufferedWriter, String message, String userName)
            throws IOException {
        bufferedWriter.write(MessageFormat.format("[{0}]: {1}", userName, message));
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public static void broadcastUserName(BufferedWriter bufferedWriter, String userName) throws IOException{
        bufferedWriter.write(userName);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
