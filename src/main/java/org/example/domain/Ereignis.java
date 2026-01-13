package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore; // Optional, daca vrei sa nu scrie computedPoints in JSON

public class Ereignis extends Entity<Integer> {
    private int tributId;
    private EventTyp type;
    private int points;
    private int day;


    public Ereignis() {
    }

    public Ereignis(Integer id, int tributId, EventTyp type, int points, int day) {
        this.setId(id);
        this.tributId = tributId;
        this.type = type;
        this.points = points;
        this.day = day;
    }


    public int getTributId() { return tributId; }
    public EventTyp getType() { return type; }
    public int getPoints() { return points; }
    public int getDay() { return day; }


    public void setTributId(int tributId) { this.tributId = tributId; }
    public void setType(EventTyp type) { this.type = type; }
    public void setPoints(int points) { this.points = points; }
    public void setDay(int day) { this.day = day; }

    // Logica de business (Cerinta 5)
    // Punem @JsonIgnore ca să NU scrie campul "computedPoints" an events.json cand salvezi,
    // deși nu e critic dacă nu pui annotarea.
    @JsonIgnore
    public int getComputedPoints() {
        if (type == null) return points; // Safety check
        switch (type) {
            case FOUND_SUPPLIES: return points + 2 * day;
            case INJURED:        return points - day;
            case ATTACK:         return points * 2 + day;
            case HELPED_ALLY:    return points + 5;
            case SPONSORED:      return points + 10;
            default:             return points;
        }
    }
}