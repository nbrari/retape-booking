package booking_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE = "booking.exchange";
    public static final String ROUTING_KEY = "booking.created";

    public void publishBookingCreated(Long bookingId, Long customerId, Long resourceId) {
        String message = "BOOKING_CREATED:" + bookingId + ":" + customerId + ":" + resourceId;
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Published booking event: " + message);
    }
}
