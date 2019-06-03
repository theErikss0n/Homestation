package homeStation.rest.entities;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "home")
@XmlAccessorType(XmlAccessType.FIELD)
public class Home {

    @XmlAttribute
    private Integer size;

    @XmlElement
    private List<Room> rooms;

    public Integer getSize(){
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}

