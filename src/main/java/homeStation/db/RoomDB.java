package homeStation.db;

import homeStation.rest.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * RoomDB simulates a simple database and provides basic crud-functionality
 */
public class RoomDB {
    private static Map<Integer, Room> roomnDB = new ConcurrentHashMap<Integer, Room>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public static Integer createRoom(String content){
        Room room = new Room();
        room.setId(idCounter.incrementAndGet());
        room.setName(content);
        roomnDB.put(room.getId(), room);

        return room.getId();
    }

    public static Room getRoom(Integer id){
        return roomnDB.get(id);
    }

    public static List<Room> getAllRooms(){
        return new ArrayList<Room>(roomnDB.values());
    }

    public static Room removeRoom(Integer id){
        return roomnDB.remove(id);
    }

    public static Room updateRoom(Integer id, Room room){
        return roomnDB.put(id, room);
    }
}