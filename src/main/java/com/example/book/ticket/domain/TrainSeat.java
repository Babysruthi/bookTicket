package com.example.book.ticket.domain;

public class TrainSeat {

    private Long seatNo;

    private Boolean isBooked;

    public TrainSeat(Long seatNo, Boolean isBooked) {
        this.seatNo = seatNo;
        this.isBooked = isBooked;
    }

    public Long getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Long seatNo) {
        this.seatNo = seatNo;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean booked) {
        isBooked = booked;
    }
}
