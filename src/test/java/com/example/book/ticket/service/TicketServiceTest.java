package com.example.book.ticket.service;

import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.service.impl.TicketServiceImpl;
import com.example.book.ticket.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(TestUtils.getTicketResponse());
        ticketService = new TicketServiceImpl(TestUtils.getTrainJourney(),ticketList,TestUtils.getSectionAndSeatMap());
    }

    @Test
    void doBookTicketForUser(){
        Ticket toBook = TestUtils.getTicket();
        toBook.getUser().setEmail("sruthi01@yopmail.com");
       Ticket ticket = ticketService.doBookTicketForUser(toBook);
       assertNotNull(ticket);
    }

    @Test
    void doBookTicketForAlreadyBookedUser(){
        Ticket ticket = ticketService.doBookTicketForUser(TestUtils.getTicket());
        assertNull(ticket);
    }

    @Test
    void doGetTicketBookedDetailsForUser(){
        Ticket ticket = ticketService.doGetBookedTicketDetailByMailId("sruthi@gmail.com");
        assertNotNull(ticket);
    }

    @Test
    void doGetDetailsForNotBookedUser(){
        Ticket ticket = ticketService.doGetBookedTicketDetailByMailId("sruthi22@gmail.com");
        assertNull(ticket);
    }

    @Test
    void doGetBookedDetailsBySection(){
        List<Ticket> ticket = ticketService.doGetBookedTicketDetailBySectionName("Section A");
        assertEquals(1L,ticket.size());
    }

    @Test
    void doRemoveTicketForUser(){
        Ticket ticket = ticketService.doRemoveUserFromTrain("sruthi@gmail.com");
        assertNotNull(ticket);
    }
}
