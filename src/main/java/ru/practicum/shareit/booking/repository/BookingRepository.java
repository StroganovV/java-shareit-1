package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // *** запросы в разных вариантах для тренировки ***

    List<Booking> findByBookerIdOrderByCreatedDesc(Long bookerId, Pageable pageable);

    List<Booking> findByBookerIdAndEndBeforeOrderByCreatedDesc(Long bookerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByBookerIdAndStatusOrderByCreatedDesc(Long bookerId, BookingStatus status, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1  " +
            "AND b.start < ?2 " +
            "AND b.end > ?2 " +
            "ORDER BY b.created DESC")
    List<Booking> getCurrentBookings(Long bookerId, LocalDateTime now, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1  " +
            "AND b.start > ?2 " +
            "ORDER BY b.created DESC")
    List<Booking> getFutureBookings(Long bookerId, LocalDateTime now, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "JOIN Item i ON i.id = b.item.id " +
            "WHERE i.ownerId = ?1 " +
            "AND b.start < ?2 " +
            "AND b.end > ?2 " +
            "ORDER BY b.created DESC")
    List<Booking> findAllCurrentBookings(long ownerId, LocalDateTime now, Pageable pageable);

    List<Booking> getBookingsByItemOwnerIdOrderByCreatedDesc(long ownerId, Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByCreatedDesc(long ownerId, LocalDateTime now, Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByCreatedDesc(long ownerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByItemOwnerIdAndStatusOrderByCreatedDesc(long ownerId, BookingStatus status, Pageable pageable);

    List<Booking> getAllByItemIdOrderByCreatedDesc(long itemID);

    Optional<Booking> findByBookerIdAndItemIdAndEndBefore(long bookerId, Long item_id, LocalDateTime end);
}
