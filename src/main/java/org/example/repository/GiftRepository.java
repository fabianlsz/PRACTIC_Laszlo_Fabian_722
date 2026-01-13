package org.example.repository;

import org.example.domain.SponsorGeschenk;

public class GiftRepository extends FileRepository<SponsorGeschenk> {
    public GiftRepository() {
        super("gifts.json", SponsorGeschenk.class);
    }
}