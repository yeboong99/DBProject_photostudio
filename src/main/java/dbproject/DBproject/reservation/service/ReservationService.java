package dbproject.DBproject.reservation.service;

import dbproject.DBproject.reservation.domain.Reservation;
import dbproject.DBproject.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> getReservationsByDate(LocalDate date) {
        return reservationRepository.findByReservationDate(date);
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public void updateReservation(Integer id, Reservation updatedReservation) {
        Reservation reservation = getReservationById(id);
        reservation.setCustomerId(updatedReservation.getCustomerId());
        reservation.setReservationDate(updatedReservation.getReservationDate());
        reservation.setStartTime(updatedReservation.getStartTime());
        reservation.setEndTime(updatedReservation.getEndTime());
        reservation.setEquipments(updatedReservation.getEquipments());
        reservation.setRoomId(updatedReservation.getRoomId());
        reservation.setTotalPrice(updatedReservation.getTotalPrice());
        reservationRepository.save(reservation);
    }

    public List<Integer> getDaysOfMonth(int year, int month) {
        List<Integer> days = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);
        int daysInMonth = date.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(i);
        }
        return days;
    }
}
