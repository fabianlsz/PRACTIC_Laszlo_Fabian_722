package org.example.repository;

import org.example.domain.Ereignis;

public class EventRepository extends FileRepository<Ereignis> {
    public EventRepository() {
        super("events.json", Ereignis.class);
    }
}