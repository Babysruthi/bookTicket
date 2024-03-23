package com.example.book.ticket;

import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.domain.TrainJourney;
import com.example.book.ticket.domain.TrainSeat;
import com.example.book.ticket.domain.TrainSection;
import com.example.book.ticket.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SeedingDataConfiguration {

    private static final String SECTION_A = "section A";
    private static final String SECTION_B = "section B";
    private static final String LONDON = "London";
    public static List<Ticket> ticketsBooked = new ArrayList<>();


    @Bean
    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User("sruthi","sekar","sruthi@gmail.com"));
        users.add(new User("pattu","chinna","pattu@gmail.com"));
        users.add(new User("durga","sri","durga@gmail.com"));
        return users;
    }

    @Bean
    public static Map<String,List<TrainSeat>> getTrainSections(){
        List<TrainSection> trainSections = new ArrayList<>();
        List<TrainSeat> trainSeats = new ArrayList<>();
        getTrainSeats(trainSeats);
        trainSections.add(new TrainSection(SECTION_A, trainSeats));
        trainSections.add(new TrainSection(SECTION_B,trainSeats));
        return trainSections.stream().collect(Collectors.toMap(TrainSection::getSectionName,TrainSection::getTrainSeats));

    }

    private static void getTrainSeats(List<TrainSeat> trainSeats) {
        for(int i=1;i<=20;i++){
            trainSeats.add(new TrainSeat((long)i,false));
        }
    }

    @Bean
    public static List<TrainJourney> getTrainJourneyList(){
        List<TrainJourney> trainJourneyList = new ArrayList<>();
        trainJourneyList.add(new TrainJourney(LONDON,"France","$5"));
        trainJourneyList.add(new TrainJourney(LONDON,"Marne-la-Vallee", "$1"));
        trainJourneyList.add(new TrainJourney(LONDON,"Canterbury", "$2"));
        trainJourneyList.add(new TrainJourney(LONDON,"Hastings", "$3"));
        trainJourneyList.add(new TrainJourney(LONDON,"Calais","$4"));
        return trainJourneyList;
    }



}
