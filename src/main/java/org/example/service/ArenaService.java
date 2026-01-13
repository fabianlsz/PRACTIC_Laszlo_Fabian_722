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

    //cerinta 5
    public List<Ereignis> getFirstNEvents(int n) {
        return eventRepo.findAll().stream()
                .limit(n)
                .collect(Collectors.toList());
    }
}