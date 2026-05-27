package booking_service.service;

import booking_service.messaging.BookingEventPublisher;
import booking_service.model.Booking;
import booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingEventPublisher eventPublisher;

    public Booking createBooking(Booking booking) {
        booking.setStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());
        Booking saved = bookingRepository.save(booking);
        eventPublisher.publishBookingCreated(
            saved.getId(), saved.getCustomerId(), saved.getResourceId()
        );
        return saved;
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}
