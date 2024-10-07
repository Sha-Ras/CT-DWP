package uk.gov.dwp.uc.pairtest;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceImplTest {
	
	private TicketTypeRequest adultTicket, childTicket, infantTicket;
	
	@BeforeEach
	void setUp(){
		adultTicket = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
		childTicket = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
		infantTicket = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
	}

	@Test
	public void testAllTicketTypeRequest(){
		
		assertEquals(TicketTypeRequest.Type.ADULT, adultTicket.getTicketType(), "Ticket type should be ADULT");
		assertEquals(2, adultTicket.getNoOfTickets(), "Number of tickets should be 2");
		
		assertEquals(TicketTypeRequest.Type.CHILD, childTicket.getTicketType(), "Ticket type should be CHILD");
		assertEquals(3, childTicket.getNoOfTickets(), "Number of tickets should be 3");
		
		assertEquals(TicketTypeRequest.Type.INFANT, infantTicket.getTicketType(), "Ticket type should be INFANT");
		assertEquals(1, infantTicket.getNoOfTickets(), "Number of tickets should be 1");
		
	}
}