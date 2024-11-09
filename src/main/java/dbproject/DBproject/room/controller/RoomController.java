package dbproject.DBproject.room.controller;


import dbproject.DBproject.room.domain.Room;
import dbproject.DBproject.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 룸 전체 정보 가져오기
    @GetMapping("/rooms")
    public String roomData(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);

        return "roomHome";
    }

    // 룸 새로만들기 누르면 폼이 뜹니다.
    @GetMapping("/rooms/new")
    public String newRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "roomForm";
    }

    // 새 룸 만들기에서 룸 정보 입력 후 저장 누르면 DB에 INSERT 후 리다이렉트(룸 리스트)
    @PostMapping("/rooms")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    // (id) 해당 룸 정보 편집창으로 넘어감. id로 조회해서 가져온 정보를 폼 창에 이미 입력되어있는 형태로 보여줌.
    @GetMapping("rooms/edit/{id}")
    public String editRoomForm(@PathVariable("id") Integer id, Model model) {
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "roomForm";
    }

    // 편집 중 저장 누르면 해당 id room에 DB 반영(UPDATE) 후 룸 리스트로 리다이렉트
    @PostMapping("/rooms/edit/{id}")
    public String updateRoom(@PathVariable("id") Integer id, @ModelAttribute Room room) {
        room.setId(id);
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    // 삭제할 id와 함께 Get 요청 시 해당 id DB에서 찾아서 삭제(DELETE) 후 룸리스트로 리다이렉트
    @GetMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable("id") Integer id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}
