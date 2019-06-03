package homeStation.rest.entities;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sensordata")
@XmlAccessorType(XmlAccessType.FIELD)
public class SensorData {
    @XmlAttribute
    private Integer size;

    @XmlElement
    private List<ClimateData> data;

    public Integer getSize(){
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<ClimateData> getRooms() {
        return data;
    }

    public void setRooms(List<ClimateData> data) {
        this.data = data;
    }

}
