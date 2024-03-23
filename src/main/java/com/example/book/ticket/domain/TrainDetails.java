package com.example.book.ticket.domain;

import java.util.List;

public class TrainDetails {

    private List<TrainSection> trainSectionList;

    public List<TrainSection> getTrainSectionList() {
        return trainSectionList;
    }

    public void setTrainSectionList(List<TrainSection> trainSectionList) {
        this.trainSectionList = trainSectionList;
    }
}
