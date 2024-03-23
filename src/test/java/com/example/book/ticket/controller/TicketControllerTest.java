package com.example.book.ticket.controller;

import com.example.book.ticket.constants.RestURIConstants;
import com.example.book.ticket.domain.Ticket;
import com.example.book.ticket.domain.TrainJourney;
import com.example.book.ticket.domain.User;
import com.example.book.ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    private MockMvc mockMvc;

    private final String BASE_URL = "/ticket";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void bookTicketForAnUserOnSuccess() throws Exception{
        Mockito.when(ticketService.doBookTicketForUser(Mockito.any())).thenReturn(getTicketResponse());
        this.mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + RestURIConstants.BOOK).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTicket())))
                        .andExpect(status().isCreated());
    }

    @Test
    void bookTicketForAlreadyBookedUser() throws Exception{
        Mockito.when(ticketService.doBookTicketForUser(Mockito.any())).thenReturn(null);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + RestURIConstants.BOOK).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTicket())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void bookTicketIfNoAvailableSeats() throws Exception{
        Ticket ticket = getTicketResponse();
        ticket.getTrainJourney().setSeatNo(null);
        Mockito.when(ticketService.doBookTicketForUser(Mockito.any())).thenReturn(ticket);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + RestURIConstants.BOOK).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTicket())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void doGetBookedUserDetails() throws Exception{
        Mockito.when(ticketService.doGetBookedTicketDetailByMailId(Mockito.anyString())).thenReturn(getTicketResponse());
        this.mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + RestURIConstants.BOOKED_USER_DETAILS).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("sruthi@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    void doGetDetailsForNotBookedUser() throws Exception{
        Mockito.when(ticketService.doGetBookedTicketDetailByMailId(Mockito.anyString())).thenReturn(null);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL + RestURIConstants.BOOKED_USER_DETAILS).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("sruthi@gmail.com")))
                .andExpect(status().isNoContent());
    }

    @Test
    void doGetDetailsForBookedUserSectionWise() throws Exception{
        Mockito.when(ticketService.doGetBookedTicketDetailBySectionName(Mockito.anyString())).thenReturn(List.of(getTicketResponse()));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URL + RestURIConstants.BOOKED_USER_DETAILS_SECTION_WISE.replace("{sectionName}","Section A")).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void doGetDetailsForNotBookedUserSectionWise() throws Exception{
        Mockito.when(ticketService.doGetBookedTicketDetailBySectionName(Mockito.anyString())).thenReturn(null);
        this.mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URL + RestURIConstants.BOOKED_USER_DETAILS_SECTION_WISE.replace("{sectionName}","Section A")).contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void doRemoveUserForBookedUser() throws Exception{
        Mockito.when(ticketService.doRemoveUserFromTrain(Mockito.anyString())).thenReturn(getTicketResponse());
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URL + RestURIConstants.REMOVE_USER).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("sruthi@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    void doRemoveUserForNotBookedUser() throws Exception{
        Mockito.when(ticketService.doRemoveUserFromTrain(Mockito.anyString())).thenReturn(null);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URL + RestURIConstants.REMOVE_USER).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("sruthi@gmail.com")))
                .andExpect(status().isNoContent());
    }

    private Ticket getTicketResponse() {
        Ticket ticket = getTicket();
        ticket.getTrainJourney().setSeatNo(10L);
        ticket.getTrainJourney().setSectionName("Section A");
        return ticket;
    }

    private Ticket getTicket() {
        Ticket ticket = new Ticket();
        User user = new User("sruthi","sekar","sruthi@gmail.com");
        TrainJourney trainJourney = new TrainJourney("London","France","$5");
        ticket.setUser(user);
        trainJourney.setFareInDollars(null);
        ticket.setTrainJourney(trainJourney);
        return ticket;
    }
}
