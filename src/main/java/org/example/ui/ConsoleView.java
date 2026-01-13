package org.example.ui;

import org.example.domain.Ereignis;
import org.example.domain.Tribut;
import org.example.service.ArenaService;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final ArenaService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(ArenaService service) {
        this.service = service;
    }

    public void start() {
        System.out.println("=== Hunger Games Arena Manager ===");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Show Statistics (Load Data)");
            System.out.println("2. Filter Tributes by District");
            System.out.println("3. Show Sorted Tributes");
            System.out.println("4. Save Sorted Tributes to File");
            System.out.println("5. Show Computed Points (First 5 Events)");
            System.out.println("6. Show Ranking (Top 5)");
            System.out.println("7. Generate Arena Report");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    uiShowStatistics();
                    break;
                case "2":
                    uiFilterByDistrict();
                    break;
                case "3":
                    uiShowSortedTributes();
                    break;
                case "4":
                    uiSaveSortedToFile();
                    break;
                case "5":
                    uiShowComputedPoints();
                    break;
                case "6":
                    uiShowRanking();
                    break;
                case "7":
                    uiGenerateReport();
                    break;
                case "0":
                    System.out.println("Exiting Application!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void uiShowStatistics() {
        System.out.println("\n--- Data Statistics ---");
        System.out.println("Tributes loaded: " + service.getAllTributes().size());
        System.out.println("Events loaded: " + service.getEventsCount());
        System.out.println("Gifts loaded: " + service.getGiftsCount());

        System.out.println("List of all Tributes:");
        service.getAllTributes().forEach(System.out::println);
    }

    private void uiFilterByDistrict() {
        try {
            System.out.print("\nEnter District ID to filter: ");
            int districtId = Integer.parseInt(scanner.nextLine());

            System.out.println("Results for District " + districtId + " (Status: ALIVE):");
            List<Tribut> filtered = service.filterTributes(districtId);

            if (filtered.isEmpty()) {
                System.out.println("No ALIVE tributes found in this district.");
            } else {
                filtered.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private void uiShowSortedTributes() {
        System.out.println("\n--- Tributes Sorted (Skill DESC, Name ASC) ---");
        service.getSortedTributes().forEach(System.out::println);
    }

    private void uiSaveSortedToFile() {
        System.out.println("\n--- Saving to File ---");
        service.saveSortedTributes();
        System.out.println("Success! Tributes saved to 'tributes_sorted.txt'.");
    }

    private void uiShowComputedPoints() {
        System.out.println("\n--- Computed Points (First 5 Events) ---");
        List<Ereignis> events = service.getFirstNEvents(5);
        for (Ereignis e : events) {
            System.out.println("Event ID: " + e.getId() +
                    " | Raw Points: " + e.getPoints() +
                    " | Computed: " + e.getComputedPoints());
        }
    }

    private void uiShowRanking() {
        System.out.println("\n--- Top 5 Ranking ---");
        var ranking = service.getTop5Ranking();
        int rank = 1;
        for (var entry : ranking) {
            System.out.println(rank + ". " + entry.getKey().getName() + " -> " + entry.getValue() + " points");
            rank++;
        }
    }

    private void uiGenerateReport() {
        System.out.println("\n--- Generating Report ---");
        service.generateArenaReport();
        System.out.println("Success! Report saved to 'arena_report.txt'.");
    }

}