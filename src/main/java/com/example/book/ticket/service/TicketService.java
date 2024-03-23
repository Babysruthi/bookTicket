package com.example.book.ticket.service;

import com.example.book.ticket.domain.Ticket;

import java.util.List;

public interface TicketService {

    Ticket doBookTicketForUser(Ticket ticket);

    Ticket doGetBookedTicketDetailByMailId(String email);

    List<Ticket> doGetBookedTicketDetailBySectionName(String sectionName);

    Ticket doRemoveUserFromTrain(String email);

    Ticket doUpdateUserSeatDetails(String email);
}
