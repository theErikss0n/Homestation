package sensor;

import sensor.entities.ClimateData;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.InetAddress;
import java.net.URI;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * The Sensor class generates data and sends it via HTTP Post
 */
public class Sensor {

    private static final Logger log = Logger.getLogger(Sensor.class.getName());
    private String type;
    private InetAddress ip;
    private int interval;

    /*
     * base-URI
     */
    private static URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost:9998/sensordata").build();
    }


    /*
     * Constructor
     */
    public Sensor(String type, String interval) {
        // Assignments
        this.interval = Integer.parseInt(interval);
        this.type = type;
    }

    /*
     * Running-loop
     */
    public void run() {
        float value = 0;
        while (true) {
            try {
                Thread.sleep(this.interval * 1000); // seconds to ms
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.log(Level.WARNING, "Thread got interrupted");
            }
            value = generateData(value); //use last value for next iteration
        }
    }

    /*
     * generate data with randomwalk and send it to homeStation
     */
    private float generateData(float lastValue) {
        // Assignments
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("#.00");
        float min;
        float max;
        String unit;
        float step;

        // Configure datagenerator
        switch (this.type) {
            case "temperature":
                min = 10;   // minimum temperature
                max = 19;   // maximum temperature
                step = 3;
                unit = "°C";
                break;

            case "humidity":
                min = 10;   // minimum humidity
                max = 70;   // maximum humidity
                step = 2;
                unit = "%";
                break;

            case "rain":
                min = 0;
                max = 10;
                step = 2;
                unit = "mm/m²";
                break;

            case "wind":
                min = 0;
                max = 45;
                step = 2;
                unit = "km/h";
                break;

            default:
                min = 0;
                max = 100;
                step = 1;
                unit = "units";
        }


        // Generate data with random walk
        float delta = rand.nextFloat();
        boolean bool = rand.nextBoolean();
        if (!bool) {
            lastValue += delta;
        } else {
            lastValue -= delta;
        }
        if (lastValue < min) {
            lastValue = min + step;
        } else if (lastValue > max) {
            lastValue = max - step;
        }

        //Create Timestamp
        String timeStamp = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
                .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));

        // create Datapackage
        ClimateData climateData = new ClimateData();
        climateData.setType(this.type);
        climateData.setValue(lastValue);
        climateData.setUnit(unit);
        climateData.setTimestamp(timeStamp);

        // send Datapackage via HTTP-POST
        Client client = ClientBuilder.newClient();
        WebTarget service = client.target(getBaseUri());
        Response response = service.request().post(
                Entity.entity(climateData, MediaType.APPLICATION_JSON), Response.class);

        log.log(Level.INFO, String.valueOf(response.getStatus()));

        // return value for next iteration
        return lastValue;
    }
}