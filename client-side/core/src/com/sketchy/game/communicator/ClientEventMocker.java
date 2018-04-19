package com.sketchy.game.communicator;

import com.sketchy.game.Config;

import java.net.URISyntaxException;
import java.util.Scanner;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Connects to the node.js server via socket.io, and can be used to send events
 * to the server. Does not interact with the client in any way.
 */
public class ClientEventMocker {
    public static void main(String[] args) {
        Socket socket;
        try {
            socket = IO.socket(Config.SERVER_ADDRESS);
            socket.connect();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input event name: ");
            while (scanner.hasNext()) {
                String eventName = scanner.nextLine().trim();
                if (Event.exists(eventName)) {
                    socket.emit(eventName);
                    System.out.println(String.format("Event '%s' emitted.", eventName));
                } else {
                    System.out.println("No such event exists");
                }
                System.out.print("Input event name: ");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
