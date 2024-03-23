package com.example.book.ticket.service.impl;

import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.domain.TrainJourney;
import com.example.book.ticket.domain.TrainSeat;
import com.example.book.ticket.service.TicketService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final List<TrainJourney> trainJourneyList;
    private final List<Ticket> ticketsBooked;
    private final Map<String,List<TrainSeat>> trainSectionAndSeatMapBySectionName;

    public TicketServiceImpl(List<TrainJourney> trainJourneyList, List<Ticket> ticketsBooked, Map<String, List<TrainSeat>> trainSectionAndSeatMapBySectionName) {
        this.trainJourneyList = trainJourneyList;
        this.ticketsBooked = ticketsBooked;
        this.trainSectionAndSeatMapBySectionName = trainSectionAndSeatMapBySectionName;
    }

    @Override
    public Ticket doBookTicketForUser(Ticket ticket) {
        if(Objects.nonNull(ticket)){
            Optional<Ticket> isAlreadyBookedUser = CollectionUtils.isNotEmpty(ticketsBooked) ? ticketsBooked.stream().filter(ticketBooked -> StringUtils.equalsIgnoreCase(ticketBooked.getUser().getEmail(),ticket.getUser().getEmail())).findAny() : Optional.empty();
            if(isAlreadyBookedUser.isEmpty()) {
                Optional<TrainJourney> trainJourneyToBook = this.trainJourneyList.stream().filter(trainJourney -> StringUtils.equalsIgnoreCase(trainJourney.getFrom(), ticket.getTrainJourney().getFrom()) && StringUtils.equalsIgnoreCase(trainJourney.getTo(), ticket.getTrainJourney().getTo())).findAny();
                bookTicketForUser(ticket, trainJourneyToBook);
            } else {
                return null;
            }
        }
        return ticket;
    }

    @Override
    public Ticket doGetBookedTicketDetailByMailId(String email) {
        if(StringUtils.isNotBlank(email)){
            return ticketsBooked.stream().filter(ticketBooked -> StringUtils.equalsIgnoreCase(ticketBooked.getUser().getEmail(),email)).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<Ticket> doGetBookedTicketDetailBySectionName(String sectionName) {
        if(StringUtils.isNotBlank(sectionName)){
            return ticketsBooked.stream().filter(ticketBooked -> StringUtils.equalsIgnoreCase(ticketBooked.getTrainJourney().getSectionName(),sectionName)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Ticket doRemoveUserFromTrain(String email) {
        if(StringUtils.isNotBlank(email)){
            Ticket ticket = ticketsBooked.stream().filter(ticketBooked -> StringUtils.equalsIgnoreCase(ticketBooked.getUser().getEmail(),email)).findAny().orElse(null);
            if (Objects.nonNull(ticket)) {
                ticketsBooked.remove(ticket);
            }
            return ticket;
        }
        return null;
    }

    @Override
    public Ticket doUpdateUserSeatDetails(String email) {
        if(StringUtils.isNotBlank(email)){
            Ticket previousBooked = doGetBookedTicketDetailByMailId(email);
            if(Objects.nonNull(previousBooked)) {
                Optional<TrainJourney> trainJourneyToBook = this.trainJourneyList.stream().filter(trainJourney -> StringUtils.equalsIgnoreCase(trainJourney.getFrom(), previousBooked.getTrainJourney().getFrom()) && StringUtils.equalsIgnoreCase(trainJourney.getTo(), previousBooked.getTrainJourney().getTo())).findAny();
                bookTicketForUser(previousBooked, trainJourneyToBook);
                return previousBooked;
            }
        }
        return null;
    }

    private void bookTicketForUser(Ticket ticket, Optional<TrainJourney> trainJourneyToBook) {
        if (trainJourneyToBook.isPresent()) {
            ticket.getTrainJourney().setFareInDollars(trainJourneyToBook.get().getFareInDollars());
            if(MapUtils.isNotEmpty(trainSectionAndSeatMapBySectionName)) {
                List<String> sectionNames = trainSectionAndSeatMapBySectionName.keySet().stream().toList();
                Long seatNo = null;
                String section = null;
                // to modify seat, removing previously present object
                if(CollectionUtils.isNotEmpty(ticketsBooked) && ticketsBooked.stream().anyMatch(bookedTicket -> StringUtils.equalsIgnoreCase(bookedTicket.getUser().getEmail(),ticket.getUser().getEmail()))){
                    Ticket prevTicket = ticketsBooked.stream().filter(bookedTicket -> StringUtils.equalsIgnoreCase(bookedTicket.getUser().getEmail(),ticket.getUser().getEmail())).findFirst().orElse(null);
                    if (Objects.nonNull(prevTicket)) {
                        section = prevTicket.getTrainJourney().getSectionName();
                        seatNo = prevTicket.getTrainJourney().getSeatNo();
                        ticketsBooked.remove(prevTicket);
                    }
                }

                for(String sectionName : sectionNames) {
                    TrainSeat trainSeatToBook = trainSectionAndSeatMapBySectionName.get(sectionName).stream().filter(trainSeat -> BooleanUtils.isFalse(trainSeat.getIsBooked())).findAny().orElse(null);
                    if(Objects.nonNull(trainSeatToBook)) {
                        ticket.getTrainJourney().setSectionName(sectionName);
                        ticket.getTrainJourney().setSeatNo(trainSeatToBook.getSeatNo());
                        trainSeatToBook.setIsBooked(true);
                        ticketsBooked.add(ticket);

                        //remove already exist seat for this user
                        if(StringUtils.isNotBlank(section) && Objects.nonNull(seatNo)) {
                            List<TrainSeat> seats = trainSectionAndSeatMapBySectionName.get(section);
                            Long finalSeatNo = seatNo;
                            seats.stream()
                                    .filter(seat -> Objects.equals(seat.getSeatNo(), finalSeatNo))
                                    .findFirst()
                                    .ifPresent(seat -> seat.setIsBooked(false));
                        }
                        break;
                    }
                }
            }
        }
    }

}
