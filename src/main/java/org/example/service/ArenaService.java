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

    //cerinta 1 statistici
    public List<Tribut> getAllTributes() {
        return tributeRepo.findAll();
    }

    public int getEventsCount() {
        return eventRepo.findAll().size();
    }

    public int getGiftsCount() {
        return giftRepo.findAll().size();
    }


}