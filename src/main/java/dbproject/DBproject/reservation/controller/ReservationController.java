package dbproject.DBproject.reservation.controller;

import dbproject.DBproject.Customers.domain.Customers;
import dbproject.DBproject.Equipment.domain.Equipment;
import dbproject.DBproject.reservation.domain.Reservation;
import dbproject.DBproject.reservation.service.ReservationService;
import dbproject.DBproject.Equipment.service.EquipmentService;
import dbproject.DBproject.Customers.service.CustomersService;
import dbproject.DBproject.room.domain.Room;
import dbproject.DBproject.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final EquipmentService equipmentService;
    private final RoomService roomService;
    private final CustomersService customerService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping
    public String showCalendar(Model model) {
        int year = 2024;
        int month = 6;
        List<String> days = IntStream.rangeClosed(1, LocalDate.of(year, month, 1).lengthOfMonth())
                .mapToObj(day -> String.format("%02d", day))
                .collect(Collectors.toList());
        model.addAttribute("days", days);
        model.addAttribute("month", String.format("%02d", month));
        model.addAttribute("year", year);
        return "reservation_calendar";
    }

    @GetMapping("/date/{date}")
    public String getReservationsByDate(@PathVariable("date") String dateString, Model model) {
        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        List<Reservation> reservations = reservationService.getReservationsByDate(date);
        model.addAttribute("reservations", reservations);
        model.addAttribute("newReservation", new Reservation());
        model.addAttribute("date", dateString);
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        model.addAttribute("roomList", roomService.getAllRooms());
        model.addAttribute("customerList", customerService.getAllCustomers());
        return "reservation_list";
    }

    @GetMapping("/addForm/{date}")
    public String showAddForm(@PathVariable("date") String dateString, Model model) {
        model.addAttribute("date", dateString);
        model.addAttribute("customerList", customerService.getAllCustomers());
        model.addAttribute("roomList", roomService.getAllRooms());
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        return "reservation_add_form";
    }

    @PostMapping("/add")
    public String addReservation(@ModelAttribute Reservation newReservation,
                                 @RequestParam("date") String dateString,
                                 @RequestParam("timeSlot") String timeSlot,
                                 @RequestParam("equipmentIds") List<Integer> equipmentIds,
                                 Model model) {
        if (equipmentIds.size() != 4) {
            model.addAttribute("errorMessage", "반드시 4개의 물품을 선택해야 합니다.");
            model.addAttribute("customerList", customerService.getAllCustomers());
            model.addAttribute("roomList", roomService.getAllRooms());
            model.addAttribute("equipmentList", equipmentService.getAllEquipment());
            model.addAttribute("date", dateString);
            return "reservation_add_form";
        }

        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
        String[] times = timeSlot.split("-");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);

        newReservation.setReservationDate(date);
        newReservation.setStartTime(startTime);
        newReservation.setEndTime(endTime);
        List<Equipment> equipments = equipmentService.getEquipmentsByIds(equipmentIds);
        newReservation.setEquipments(equipments);

        // 총 가격 계산 (예: 물품 가격 합산)
        int totalPrice = equipments.stream().mapToInt(Equipment::getPrice).sum() + newReservation.getRoomId().getPrice();
        newReservation.setTotalPrice(totalPrice);

        reservationService.addReservation(newReservation);
        return "redirect:/reservation/date/" + dateString;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Integer id,
                                    @RequestParam("date") String dateString) {
        reservationService.deleteReservation(id);
        return "redirect:/reservation/date/" + dateString;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        model.addAttribute("roomList", roomService.getAllRooms());
        model.addAttribute("customerList", customerService.getAllCustomers());

        String timeSlot = reservation.getStartTime().toString() + "-" + reservation.getEndTime().toString();
        model.addAttribute("timeSlot", timeSlot);

        return "reservation_edit_form";
    }

    @PostMapping("/edit/{id}")
    public String editReservation(@PathVariable("id") Integer id,
                                  @ModelAttribute Reservation reservation,
                                  @RequestParam("timeSlot") String timeSlot,
                                  @RequestParam("equipmentIds") List<Integer> equipmentIds,
                                  Model model) {
        // 기존 예약을 가져오기
        Reservation existingReservation = reservationService.getReservationById(id);

        // timeSlot을 시작 시간과 종료 시간으로 나누기
        String[] timeSlotParts = timeSlot.split("-");
        LocalTime startTime = LocalTime.parse(timeSlotParts[0]);
        LocalTime endTime = LocalTime.parse(timeSlotParts[1]);

        // 예약 업데이트
        existingReservation.setStartTime(startTime);
        existingReservation.setEndTime(endTime);

        // 고객 및 장비 설정
        Customers customer = customerService.findById(reservation.getCustomerId().getId());
        List<Equipment> equipments = equipmentService.getEquipmentsByIds(equipmentIds);

        existingReservation.setCustomerId(customer);
        existingReservation.setEquipments(equipments);

        // 총 가격 계산
        Room room = roomService.getRoomById(reservation.getRoomId().getId());
        int totalPrice = room.getPrice() + equipments.stream().mapToInt(Equipment::getPrice).sum();
        existingReservation.setTotalPrice(totalPrice);

        reservationService.updateReservation(id, existingReservation);

        // 기존 기능을 유지하면서 "/reservation/date/{date}"로 리다이렉트
        return "redirect:/reservation/date/" + existingReservation.getReservationDate().format(DATE_FORMATTER);
    }
}
