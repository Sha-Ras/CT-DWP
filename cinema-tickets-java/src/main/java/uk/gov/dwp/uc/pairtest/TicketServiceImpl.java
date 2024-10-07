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
        
        int totalAmount = 50;
        int totalSeatsToReserve = 2;
        
        ticketPaymentService.makePayment(accountId, totalAmount);
        seatReservationService.reserveSeat(accountId, totalSeatsToReserve);
        
    }
    
    private void validateAccountAndRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests) {
        if (accountId == null || ticketTypeRequests == null || ticketTypeRequests.length == 0 || accountId <= 0) {
            throw new InvalidPurchaseException("Invalid request");
        }
    }
    
    
}
