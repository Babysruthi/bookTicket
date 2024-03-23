package com.example.book.ticket;

import com.example.book.ticket.domain.TrainJourney;
import com.example.book.ticket.domain.TrainSeat;
import com.example.book.ticket.domain.TrainSection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SeedingDataConfiguration {

    private static final String SECTION_A = "section A";
    private static final String SECTION_B = "section B";
    private static final String LONDON = "London";

    @Bean
    public static Map<String,List<TrainSeat>> getTrainSections(){
        List<TrainSection> trainSections = new ArrayList<>();
        trainSections.add(new TrainSection(SECTION_A, getTrainSeats()));
        trainSections.add(new TrainSection(SECTION_B,getTrainSeats()));
        return trainSections.stream().collect(Collectors.toMap(TrainSection::getSectionName,TrainSection::getTrainSeats));

    }

    private static List<TrainSeat> getTrainSeats() {
        List<TrainSeat> trainSeats = new ArrayList<>();
        for(int i=1;i<=20;i++){
            trainSeats.add(new TrainSeat((long)i,false));
        }
        return trainSeats;
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
