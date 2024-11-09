package dbproject.DBproject.room.service;

import dbproject.DBproject.room.domain.Room;
import dbproject.DBproject.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {    // select * from room;
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {   // update room set capacity=?,image_path=?,name=?,price=? where id=?
        return roomRepository.save(room);
    }

    public Room getRoomById(Integer id) {   // select * from room where id = ?;
        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 아이디입니다. (id : " + id + ")"));
    }

    public void deleteRoom(Integer id) {    // delete from room where id=?;
        roomRepository.deleteById(id);
    }

}
