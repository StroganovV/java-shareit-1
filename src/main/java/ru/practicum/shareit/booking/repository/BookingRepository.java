package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // *** запросы в разных вариантах для тренировки ***

    List<Booking> findByBookerIdOrderByCreatedDateDesc(Long bookerId);

    List<Booking> findByBookerIdAndEndBeforeOrderByCreatedDateDesc(Long bookerId, LocalDateTime now);

    List<Booking> findByBookerIdAndStatusOrderByCreatedDateDesc(Long bookerId, BookingStatus status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1  " +
            "AND b.start < ?2 " +
            "AND b.end > ?2 " +
            "ORDER BY b.createdDate DESC")
    List<Booking> getCurrentBookings(Long bookerId, LocalDateTime now);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1  " +
            "AND b.start > ?2 " +
            "ORDER BY b.createdDate DESC")
    List<Booking> getFutureBookings(Long bookerId, LocalDateTime now);

    @Query("SELECT b FROM Booking b " +
            "JOIN Item i ON i.id = b.item.id " +
            "WHERE i.ownerId = ?1 " +
            "AND b.start < ?2 " +
            "AND b.end > ?2 " +
            "ORDER BY b.createdDate DESC")
    List<Booking> findAllCurrentBookings(long ownerId, LocalDateTime now);

    List<Booking> getBookingsByItemOwnerIdOrderByCreatedDateDesc(long ownerId);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByCreatedDateDesc(long ownerId, LocalDateTime now);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByCreatedDateDesc(long ownerId, LocalDateTime now);

    List<Booking> findByItemOwnerIdAndStatusOrderByCreatedDateDesc(long ownerId, BookingStatus status);

    List<Booking> getAllByItemIdOrderByCreatedDateDesc(long itemID);

    Optional<Booking> findByBookerIdAndItemIdAndEndBefore(long bookerId, Long item_id, LocalDateTime end);
}
