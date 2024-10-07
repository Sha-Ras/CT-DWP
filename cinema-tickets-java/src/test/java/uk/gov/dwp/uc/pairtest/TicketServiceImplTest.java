package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

 class TicketServiceImplTest {
	
	private TicketTypeRequest adultTicket, childTicket, infantTicket;
	
	 private TicketPaymentService ticketPaymentService;
	 private SeatReservationService seatReservationService;
	 private TicketService ticketService;
	 
	@BeforeEach
	void setUp(){
		adultTicket = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
		childTicket = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
		infantTicket = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
		
		 ticketPaymentService = mock(TicketPaymentService.class);
		 seatReservationService = mock(SeatReservationService.class);
		 ticketService = new TicketServiceImpl(ticketPaymentService, seatReservationService);
	}

	@Test
	 void testAllTicketTypeRequests(){
		
		assertEquals(TicketTypeRequest.Type.ADULT, adultTicket.getTicketType(), "Ticket type should be ADULT");
		assertEquals(2, adultTicket.getNoOfTickets(), "Number of tickets should be 2");
		
		assertEquals(TicketTypeRequest.Type.CHILD, childTicket.getTicketType(), "Ticket type should be CHILD");
		assertEquals(3, childTicket.getNoOfTickets(), "Number of tickets should be 3");
		
		assertEquals(TicketTypeRequest.Type.INFANT, infantTicket.getTicketType(), "Ticket type should be INFANT");
		assertEquals(1, infantTicket.getNoOfTickets(), "Number of tickets should be 1");
		
	}
	
	@Test
	 void testHardCodedAdultTicketPurchase(){
		
		ticketService.purchaseTickets(1L, adultTicket);
		
		verify(ticketPaymentService).makePayment(1L, 50);
		verify(seatReservationService).reserveSeat(1L, 2);
	}
	 
	 @Test
	 void testAdultTicketPurchaseWithValidationToThrowsException(){
		
		 assertThrows(InvalidPurchaseException.class, () -> {
			 ticketService.purchaseTickets(0L, adultTicket);
		 });
	 }
	 
	 @Test
	 void testNullAccountIdShouldThrowsException(){
		 
		 assertThrows(InvalidPurchaseException.class, () -> {
			 ticketService.purchaseTickets(null, adultTicket);
		 });
	 }
	 
	 @Test
	 void testWhenAccountIdIsLessThanZeroShouldThrowsException(){
		 
		 assertThrows(InvalidPurchaseException.class, () -> {
			 ticketService.purchaseTickets(-1L, adultTicket);
		 });
	 }
	 
	 @Test
	 void testWhenTicketTypeRequestsIsNullShouldThrowsException(){
		 
		 assertThrows(InvalidPurchaseException.class, () -> {
			 ticketService.purchaseTickets(1L, null);
		 });
	 }
	 
	
 }