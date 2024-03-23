package com.example.book.ticket.domain;

public class Ticket {

    private User user;
    private TrainJourney trainJourney;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TrainJourney getTrainJourney() {
        return trainJourney;
    }

    public void setTrainJourney(TrainJourney trainJourney) {
        this.trainJourney = trainJourney;
    }

}
