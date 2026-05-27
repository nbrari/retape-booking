package notification_service.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendConfirmation(Long bookingId, Long customerId, Long resourceId) {
        System.out.println("=================================");
        System.out.println("NOTIFICATION SENT!");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Resource ID: " + resourceId);
        System.out.println("Email confirmation sent to customer " + customerId);
        System.out.println("=================================");
    }
}
