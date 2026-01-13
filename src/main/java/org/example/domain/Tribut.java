package org.example.domain;

public class Tribut extends Entity<Integer> {
    private String name;
    private int district;
    private Status status;
    private int skillLevel;


    public Tribut() {}

    public Tribut(Integer id, String name, int district, Status status, int skillLevel) {
        this.setId(id);
        this.name = name;
        this.district = district;
        this.status = status;
        this.skillLevel = skillLevel;
    }

    public String getName() { return name; }
    public int getDistrict() { return district; }
    public Status getStatus() { return status; }
    public int getSkillLevel() { return skillLevel; }


    public void setName(String name) { this.name = name; }
    public void setDistrict(int district) { this.district = district; }
    public void setStatus(Status status) { this.status = status; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }

    @Override
    public String toString() {
        return getId() + " | " + name + " | D" + district + " | " + status + " | skill=" + skillLevel;
    }
}