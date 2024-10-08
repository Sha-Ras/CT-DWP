package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    
    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;
    
    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }
    
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        
        validateAccountAndRequest(accountId, ticketTypeRequests);
        
        int totalAmount = 0;
        int totalSeatsToReserve = 0;
        boolean hasAdult = false;
        
        
        for(TicketTypeRequest request : ticketTypeRequests) {
            totalSeatsToReserve += calculateSeatsForRequest(request);
            totalAmount += calculateRequestTotalAmount(request);
            
            if(request.getTicketType() == TicketTypeRequest.Type.ADULT){
                hasAdult = true;
            }
        }
        
        validateAdultCondition(hasAdult);
        validateMaxTickets(ticketTypeRequests);
        
        ticketPaymentService.makePayment(accountId, totalAmount);
        seatReservationService.reserveSeat(accountId, totalSeatsToReserve);
        
    }
    
    private int calculateRequestTotalAmount(TicketTypeRequest request) {
        int ticket = request.getNoOfTickets();
        switch (request.getTicketType()){
            case ADULT:
                return ticket * 25;
            case CHILD:
                return ticket * 15;
            case INFANT:
                return 0;
            default:
                throw new IllegalArgumentException("Invalid type request");
        }
    }
    
    private int calculateSeatsForRequest(TicketTypeRequest request) {
        int tickets = request.getNoOfTickets();
        switch (request.getTicketType()){
            case ADULT:
            case CHILD:
                return tickets;
            case INFANT:
                return 0;
            default:
                throw new IllegalArgumentException("Invalid type request");
        }
	}
    
    private void validateMaxTickets(TicketTypeRequest[] ticketTypeRequests) {
        int totalTickets = 0;
        for(TicketTypeRequest request : ticketTypeRequests) {
            totalTickets += request.getNoOfTickets();
        }
        if(totalTickets > 25){
            throw new InvalidPurchaseException("Too many tickets! Cannot purchase more than 25 tickets");
        }
    }
    
    private void validateAdultCondition(boolean hasAdult) {
        if(!hasAdult){
            throw new InvalidPurchaseException("Child or Infant tickets must be accompanied by an Adult");
        }
    }
    
    private void validateAccountAndRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests) {
        if (accountId == null || ticketTypeRequests == null || ticketTypeRequests.length == 0 || accountId <= 0) {
            throw new InvalidPurchaseException("Invalid request");
        }
    }
    
    
}
