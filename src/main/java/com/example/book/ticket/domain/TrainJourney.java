package com.example.book.ticket.domain;

public class TrainJourney {

    private String from;
    private String to;
    private String fareInDollars;
    private Long seatNo;
    private String sectionName;


    public TrainJourney(String from, String to, String fareInDollars) {
        this.from = from;
        this.to = to;
        this.fareInDollars = fareInDollars;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFareInDollars() {
        return fareInDollars;
    }

    public void setFareInDollars(String fareInDollars) {
        this.fareInDollars = fareInDollars;
    }

    public Long getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Long seatNo) {
        this.seatNo = seatNo;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
