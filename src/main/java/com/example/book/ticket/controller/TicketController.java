package com.example.book.ticket.controller;

import com.example.book.ticket.constants.RestURIConstants;
import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.service.TicketService;
import com.example.book.ticket.utils.HttpStatusResponse;
import com.example.book.ticket.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")

public class TicketController {

    @Autowired
    private TicketService ticketService;


    @PostMapping(RestURIConstants.BOOK)
    public ResponseEntity<HttpStatusResponse> bookTicketAndReturnReceipt(@RequestBody Ticket ticket){
        Ticket ticketBooked = ticketService.doBookTicketForUser(ticket);
        if (Objects.isNull(ticketBooked)){
            return ResponseUtils.prepareUnProcessableResponse("Ticket booked already",null);
        } else if(Objects.isNull(ticketBooked.getTrainJourney().getSeatNo())){
            return ResponseUtils.prepareUnProcessableResponse("Tickets not available", null);
        } return ResponseUtils.prepareCreatedSuccessResponse("Ticket created successfully", null);
    }

    @PostMapping(RestURIConstants.BOOKED_USER_DETAILS)
    public ResponseEntity<HttpStatusResponse> getBookedTicketDetails(@RequestBody String email){
        return Optional.ofNullable(ticketService.doGetBookedTicketDetailByMailId(email))
                .map(ticketBooked -> ResponseUtils.prepareSuccessResponse("Fetched ticket details", ticketBooked))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No booked tickets found for this email {}",email));
    }

    @GetMapping(RestURIConstants.BOOKED_USER_DETAILS_SECTION_WISE)
    public ResponseEntity<HttpStatusResponse> getUserTicketDetailsBySection(@PathVariable("sectionName") String sectionName){
        return Optional.ofNullable(ticketService.doGetBookedTicketDetailBySectionName(sectionName))
                .map(ticketBooked -> ResponseUtils.prepareSuccessResponse("Fetched ticket details by section name", ticketBooked))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No booked tickets found for this section {}",sectionName));

    }

    @DeleteMapping(RestURIConstants.REMOVE_USER)
    public ResponseEntity<HttpStatusResponse> removeUser(@RequestBody String email){
        return Optional.ofNullable(ticketService.doRemoveUserFromTrain(email))
                .map(ticketBooked -> ResponseUtils.prepareSuccessResponse("Removed user successfully", ticketBooked))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No booked user found for this email {}",email));
    }

    @PutMapping(RestURIConstants.MODIFY_USER_BOOKING_DETAILS)
    public ResponseEntity<HttpStatusResponse> updateUserTicketDetails(@RequestBody String email){
        return Optional.ofNullable(ticketService.doUpdateUserSeatDetails(email))
                .map(ticketBooked -> ResponseUtils.prepareSuccessResponse("Fetched ticket details", ticketBooked))
                .orElse(ResponseUtils.prepareNoRecordsFoundResponse("No booked tickets found for this email {}",email));
        }
    }

