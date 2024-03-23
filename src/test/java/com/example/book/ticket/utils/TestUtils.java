package com.example.book.ticket.utils;

import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.domain.TrainJourney;
import com.example.book.ticket.domain.TrainSeat;
import com.example.book.ticket.domain.TrainSection;
import com.example.book.ticket.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestUtils {

    public static Ticket getTicketResponse() {
        Ticket ticket = getTicket();
        ticket.getTrainJourney().setSeatNo(10L);
        ticket.getTrainJourney().setSectionName("Section A");
        return ticket;
    }

    public static Ticket getTicket() {
        Ticket ticket = new Ticket();
        User user = new User("sruthi","sekar","sruthi@gmail.com");
        TrainJourney trainJourney = new TrainJourney("London","France","$5");
        ticket.setUser(user);
        trainJourney.setFareInDollars(null);
        ticket.setTrainJourney(trainJourney);
        return ticket;
    }

    public static List<TrainJourney> getTrainJourney() {
        List<TrainJourney> trainJourneyList = new ArrayList<>();
        trainJourneyList.add(new TrainJourney("LONDON","France","$5"));
        trainJourneyList.add(new TrainJourney("LONDON","Marne-la-Vallee", "$1"));
        return  trainJourneyList;
    }

    public static Map<String,List<TrainSeat>> getSectionAndSeatMap() {
        List<TrainSection> trainSections = new ArrayList<>();
        trainSections.add(new TrainSection("Section A", getTrainSeats()));
        trainSections.add(new TrainSection("Section B",getTrainSeats()));
        return trainSections.stream().collect(Collectors.toMap(TrainSection::getSectionName,TrainSection::getTrainSeats));
    }

    private static List<TrainSeat> getTrainSeats() {
        List<TrainSeat> trainSeats = new ArrayList<>();
        for(int i=1;i<=2;i++){
            trainSeats.add(new TrainSeat((long)i,false));
        }
        return trainSeats;
    }

}
