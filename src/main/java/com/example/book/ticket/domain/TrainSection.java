package com.example.book.ticket.domain;

import java.util.List;

public class TrainSection {

    private String sectionName;

    private List<TrainSeat> trainSeats;

    public TrainSection(String sectionName, List<TrainSeat> trainSeats) {
        this.sectionName = sectionName;
        this.trainSeats = trainSeats;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<TrainSeat> getTrainSeats() {
        return trainSeats;
    }

    public void setTrainSeats(List<TrainSeat> trainSeats) {
        this.trainSeats = trainSeats;
    }
}
