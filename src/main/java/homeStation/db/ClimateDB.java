package homeStation.db;

import homeStation.rest.entities.ClimateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  ClimateDB simulates a simple database and provides basic crud-functionality
 */
public class ClimateDB {

    // Container to store climateData
    private static Map<Integer, ClimateData> climateDB = new ConcurrentHashMap<Integer, ClimateData>();
    private static AtomicInteger idCounter = new AtomicInteger();

    /**
     * save new climateData in database
     * @param type dataType (eg. temperature, rain, ...)
     * @param value value
     * @param unit unit of data (e.g °C, km/h)
     * @param timestamp timestamp when data got measured
     * @return id of created item
     */
    public static Integer createClimateData(String type, Float value, String unit, String timestamp) {
        ClimateData climateData = new ClimateData();
        climateData.setId(idCounter.incrementAndGet());
        climateData.setType(type);
        climateData.setValue(value);
        climateData.setUnit(unit);
        climateData.setTimestamp(timestamp);
        climateDB.put(climateData.getId(), climateData);

        return climateData.getId();
    }

    public static ClimateData getClimateData(Integer id) {
        return climateDB.get(id);
    }

    public static List<ClimateData> getAllClimateData() {
        return new ArrayList<ClimateData>(climateDB.values());
    }

    public static ClimateData removeClimateData(Integer id) {
        return climateDB.remove(id);
    }

    public static ClimateData updateClimateData(Integer id, ClimateData climateData) {
        return climateDB.put(id, climateData);
    }
}