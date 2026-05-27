package notification_service.listener;

import notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingEventListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "booking.queue")
    public void handleBookingCreated(String message) {
        System.out.println("Received event: " + message);
        try {
            String[] parts = message.split(":");
            Long bookingId = Long.parseLong(parts[1]);
            Long customerId = Long.parseLong(parts[2]);
            Long resourceId = Long.parseLong(parts[3]);
            notificationService.sendConfirmation(bookingId, customerId, resourceId);
        } catch (Exception e) {
            System.out.println("Error processing event: " + e.getMessage());
        }
    }
}
