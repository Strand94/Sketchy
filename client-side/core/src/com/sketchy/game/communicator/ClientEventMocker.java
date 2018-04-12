package com.sketchy.game.communicator;

import java.util.Scanner;

import static com.sketchy.game.communicator.Event.*;

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

        String eventName;
        System.out.print("Input event name: ");
        while (scanner.hasNext()) {
            eventName = scanner.nextLine().trim();
            try {
                Event event = Event.get(eventName);
                communicator.emit(event);
                System.out.println(String.format("Event '%s' emitted.", eventName));
            } catch (Event.NoSuchEvent e) {
                System.out.println("No such event exists");
            }
            System.out.print("Input event name: ");
        }
    }
}
