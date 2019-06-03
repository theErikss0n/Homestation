package homeStation.rest.entities;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "room")
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {

    @XmlAttribute
    Integer id;

    @XmlElement
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
