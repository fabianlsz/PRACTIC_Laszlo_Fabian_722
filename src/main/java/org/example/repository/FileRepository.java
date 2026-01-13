package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRepository<T extends Entity<Integer>> implements IRepository<T> {
    private String filename;
    private Class<T> type;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<Integer, T> entities = new HashMap<>();
    private int currentId = 1;

    public FileRepository(String filename, Class<T> type) {
        this.filename = filename;
        this.type = type;
        loadFromFile();
    }

    @Override
    public void add(T entity) {
        if (entity.getId() == null) {
            entity.setId(currentId++);
        }
        entities.put(entity.getId(), entity);
        saveToFile();
    }

    @Override
    public void delete(Integer id) {
        entities.remove(id);
        saveToFile();
    }

    @Override
    public void update(T entity) {
        entities.put(entity.getId(), entity);
        saveToFile();
    }

    @Override
    public T findOne(Integer id) {
        return entities.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    private void saveToFile() {
        try {
            // Scrie lista de valori în fișier
            mapper.writeValue(new File(filename), new ArrayList<>(entities.values()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) return;
        try {
            // Jackson citește lista direct și o convertește în obiecte de tip T
            List<T> loadedList = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(List.class, type));

            int maxId = 0;
            for (T entity : loadedList) {
                entities.put(entity.getId(), entity);
                if (entity.getId() > maxId) maxId = entity.getId();
            }
            currentId = maxId + 1;
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului " + filename + ": " + e.getMessage());
        }
    }
}