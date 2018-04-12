package com.sketchy.game.communicator;

import java.util.Scanner;
import java.util.regex.Pattern;

import static com.sketchy.game.communicator.Events.*;

public class ClientEventMocker {
    private static class MockCommunicator extends Communicator {
        @Override
        public void createLobby() {
            emit(CREATE_LOBBY);
        }
    }

    public static void main(String[] args) {
        MockCommunicator communicator = new MockCommunicator();
        communicator.connect();
        Scanner scanner = new Scanner(System.in);
        Pattern eventPattern = Pattern.compile("[a-z-]{3,}");

        String eventName;
        System.out.print("Input event name: ");
        while (scanner.hasNext()) {
            eventName = scanner.nextLine().trim();
            if (!eventPattern.matcher(eventName).matches()) {
                System.out.println(String.format("'%s' is not a valid event name.", eventName));
                continue;
            }
            try {
                Event event = Events.get(eventName);
                communicator.emit(event);
                System.out.println(String.format("Event '%s' emitted.", eventName));
            } catch (Events.NoSuchEvent e) {
                System.out.println("No such event exists");
            }
            System.out.print("Input event name: ");
        }
    }
}
