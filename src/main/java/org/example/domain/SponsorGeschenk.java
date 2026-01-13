package org.example.domain;

public class SponsorGeschenk extends Entity<Integer> {
    private int tributId;
    private String itemName;
    private int value;
    private int day;


    public SponsorGeschenk() {
    }

    public SponsorGeschenk(Integer id, int tributId, String itemName, int value, int day) {
        this.setId(id);
        this.tributId = tributId;
        this.itemName = itemName;
        this.value = value;
        this.day = day;
    }


    public int getTributId() { return tributId; }
    public String getItemName() { return itemName; }
    public int getValue() { return value; }
    public int getDay() { return day; }


    public void setTributId(int tributId) { this.tributId = tributId; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setValue(int value) { this.value = value; }
    public void setDay(int day) { this.day = day; }
}