package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ArenaService {
    private final IRepository<Tribut> tributeRepo;
    private final IRepository<Ereignis> eventRepo;
    private final IRepository<SponsorGeschenk> giftRepo;

    public ArenaService(IRepository<Tribut> tributeRepo,
                        IRepository<Ereignis> eventRepo,
                        IRepository<SponsorGeschenk> giftRepo) {
        this.tributeRepo = tributeRepo;
        this.eventRepo = eventRepo;
        this.giftRepo = giftRepo;
    }

    // cerinta 1  statistici
    public List<Tribut> getAllTributes() {
        return tributeRepo.findAll();
    }

    public int getEventsCount() {
        return eventRepo.findAll().size();
    }

    public int getGiftsCount() {
        return giftRepo.findAll().size();
    }

    // cerinta 2 filtrare district si status
    public List<Tribut> filterTributes(int district) {
        return tributeRepo.findAll().stream()
                .filter(t -> t.getDistrict() == district && t.getStatus() == Status.ALIVE)
                .collect(Collectors.toList());
    }

    //cerinta 3 sortare skill desc nume asc
    public List<Tribut> getSortedTributes() {
        return tributeRepo.findAll().stream()
                .sorted(Comparator.comparingInt(Tribut::getSkillLevel).reversed()
                        .thenComparing(Tribut::getName))
                .collect(Collectors.toList());
    }

    //cerinta 4 salvarea sortarii in fisier tributes_sorted.json
    public void saveSortedTributes() {
        List<Tribut> sorted = getSortedTributes();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tributes_sorted.txt"))) {
            for (Tribut t : sorted) {
                writer.write(t.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Eroare la scrierea fisierului tributes_sorted.txt: " + e.getMessage());
        }
    }

    //cerinta 5 afisare puncte dupa primele 5 evenimente
    public List<Ereignis> getFirstNEvents(int n) {
        return eventRepo.findAll().stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    //cerinta 6 ranking scor
    public List<Map.Entry<Tribut, Integer>> getTop5Ranking() {
        Map<Integer, Integer> scoreMap = new HashMap<>();
        //adaugare puncte din evenimente
        for (Ereignis e : eventRepo.findAll()) {
            int currentScore = scoreMap.getOrDefault(e.getTributId(), 0);
            scoreMap.put(e.getTributId(), currentScore + e.getComputedPoints());
        }

        //adaugare puncte din geschenkuri
        for (SponsorGeschenk g : giftRepo.findAll()) {
            int currentScore = scoreMap.getOrDefault(g.getTributId(), 0);
            scoreMap.put(g.getTributId(), currentScore + g.getValue());
        }

        //asociem cu obiectele tribute
        List<Map.Entry<Tribut, Integer>> rankingList = new ArrayList<>();
        for (Tribut t : tributeRepo.findAll()) {
            int score = scoreMap.getOrDefault(t.getId(), 0);
            rankingList.add(new AbstractMap.SimpleEntry<>(t, score));
        }

        //sortare descrescatoare in functie de puncte si crescatoare pt nume
        rankingList.sort((e1, e2) -> {
            int scoreCompare = e2.getValue().compareTo(e1.getValue());
            if (scoreCompare == 0) {
                return e1.getKey().getName().compareTo(e2.getKey().getName());
            }
            return scoreCompare;
        });
        //return top 5
        return rankingList.stream().limit(5).collect(Collectors.toList());
    }

    //cerinta 7 generare raport arena_report.txt
    public void generateArenaReport() {
        //numaram evenimentele grupate dupa type
        Map<EventTyp, Long> counts = eventRepo.findAll().stream()
                .collect(Collectors.groupingBy(Ereignis::getType, Collectors.counting()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("arena_report.txt"))) {
            for (Map.Entry<EventTyp, Long> entry : counts.entrySet()) {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Eroare la generarea raportului: " + e.getMessage());
        }
    }
}