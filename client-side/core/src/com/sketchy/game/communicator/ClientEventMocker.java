package com.sketchy.game.communicator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ClientEventMocker {
    private static class MockCommunicator extends com.sketchy.game.communicator.Communicator {
        @Override
        public void createLobby() {
            emit(Event.CREATE_LOBBY);
        }
    }

    public static void main(String[] args) {
        MockCommunicator communicator = new MockCommunicator();
        communicator.connect();
        Scanner scanner = new Scanner(System.in);
        Pattern eventPattern = Pattern.compile("[a-z-]{3,}");

        String eventName;
        System.out.print("Which event to mock? ");
        while (scanner.hasNext()) {
            eventName = scanner.nextLine();
            if (!eventPattern.matcher(eventName).matches()) {
                System.out.println(String.format("'%s' is not a valid event name.", eventName));
                continue;
            }
            try {
                com.sketchy.game.communicator.Event event = com.sketchy.game.communicator.Event.get(eventName);
                communicator.emit(event);
                System.out.println(String.format("Event '%s' emitted.", eventName));
            } catch (com.sketchy.game.communicator.Event.NoSuchEvent e) {
                System.out.println("No such event exists");
            }
            System.out.print("Which event to mock? ");
        }
    }
}
