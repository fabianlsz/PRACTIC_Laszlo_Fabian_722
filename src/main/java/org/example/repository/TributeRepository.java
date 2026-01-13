package org.example.repository;

import org.example.domain.Tribut;

public class TributeRepository extends FileRepository<Tribut> {
    public TributeRepository() {
        // Asigură-te că fișierul tributes.json este în folderul corect (root proiect)
        super("tributes.json", Tribut.class);
    }
}