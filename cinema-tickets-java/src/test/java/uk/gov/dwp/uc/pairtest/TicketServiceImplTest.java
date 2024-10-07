package uk.gov.dwp.uc.pairtest;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TicketServiceImplTest {
	
	private TicketTypeRequest adultTicket, childTicket, infantTicket;
	
	@BeforeEach
	void setUp(){
		adultTicket = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
		childTicket = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
		infantTicket = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
	}

	@Test
	public void testAllTicketTypeRequests(){
		
		assertEquals(TicketTypeRequest.Type.ADULT, adultTicket.getTicketType(), "Ticket type should be ADULT");
		assertEquals(2, adultTicket.getNoOfTickets(), "Number of tickets should be 2");
		
		assertEquals(TicketTypeRequest.Type.CHILD, childTicket.getTicketType(), "Ticket type should be CHILD");
		assertEquals(3, childTicket.getNoOfTickets(), "Number of tickets should be 3");
		
		assertEquals(TicketTypeRequest.Type.INFANT, infantTicket.getTicketType(), "Ticket type should be INFANT");
		assertEquals(1, infantTicket.getNoOfTickets(), "Number of tickets should be 1");
		
	}
	
	@Test
	public void testHardCodedAdultTicketPurchase(){
		TicketPaymentService ticketPaymentService = mock(TicketPaymentService.class);
		SeatReservationService seatReservationService = mock(SeatReservationService.class);
		TicketService ticketService = mock(TicketService.class);
		
		ticketService.purchaseTickets(1L, adultTicket);
		
		verify(ticketPaymentService).makePayment(1L, 50);
		verify(seatReservationService).reserveSeat(1L, 2);
	}
}