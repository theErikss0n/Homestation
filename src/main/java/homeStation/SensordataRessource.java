package homeStation;

import homeStation.db.ClimateDB;
import homeStation.rest.entities.ClimateData;
import homeStation.rest.entities.Message;
import homeStation.rest.entities.SensorData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sensordata")
@Produces(MediaType.APPLICATION_JSON)
public class SensordataRessource {

    @GET
    public SensorData getData() {
        List<ClimateData> dataList = ClimateDB.getAllClimateData();
        SensorData sensorData = new SensorData();
        sensorData.setRooms(dataList);
        sensorData.setSize(dataList.size());
        return sensorData;
    }

    @GET
    @Path("/{id}")
    public Response getDataById(@PathParam("id") Integer id) {
        ClimateData dbData = ClimateDB.getClimateData(id);
        if (dbData == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(dbData).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createData(ClimateData climateData) {
        if (!validateClimateData(climateData)) {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("invalid data")).build();
        } else {
            Float value = climateData.getValue();
            String type = climateData.getType();
            String unit = climateData.getUnit();
            String timestamp = climateData.getTimestamp();

            ClimateDB.createClimateData(type, value, unit, timestamp);
            return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
        }
    }


    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    public Response updateData(@PathParam("id") Integer id, ClimateData climateData) {
        ClimateData dbData = ClimateDB.getClimateData(id);
        if (dbData == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        if (!validateClimateData(climateData)) {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("data content not found")).build();
        }
        ClimateDB.updateClimateData(id, climateData);
        return Response.status(javax.ws.rs.core.Response.Status.OK)
                .entity(new Message("data updated successfully")).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteData(@PathParam("id") Integer id) {
        ClimateData dbData = ClimateDB.getClimateData(id);
        if (dbData == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        ClimateDB.removeClimateData(id);
        return Response.status(javax.ws.rs.core.Response.Status.OK).build();
    }

    private boolean validateClimateData(ClimateData data){
        // validate if all fields are set
        return data.getType() != null && data.getTimestamp() != null &&
                data.getUnit() != null && data.getValue() != null;
    }

}

