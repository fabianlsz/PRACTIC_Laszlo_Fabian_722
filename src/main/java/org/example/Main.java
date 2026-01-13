package org.example;

import org.example.repository.EventRepository;
import org.example.repository.GiftRepository;
import org.example.repository.TributeRepository;
import org.example.service.ArenaService;
import org.example.ui.ConsoleView;

public class Main {
    public static void main(String[] args) {
        TributeRepository tributeRepo = new TributeRepository();
        EventRepository eventRepo = new EventRepository();
        GiftRepository giftRepo = new GiftRepository();

        ArenaService service = new ArenaService(tributeRepo, eventRepo, giftRepo);

        ConsoleView view = new ConsoleView(service);

        view.start();
    }
}