package homeStation;

import homeStation.db.RoomDB;
import homeStation.rest.entities.Home;
import homeStation.rest.entities.Message;
import homeStation.rest.entities.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResource {

    @GET
    public Home getRooms() {
        List<Room> rooms  = RoomDB.getAllRooms();
        Home home = new Home();
        home.setRooms(rooms);
        home.setSize(rooms.size());

        return home;
    }

    @GET
    @Path("/{id}")
    public Response getRoomByName(@PathParam("id") Integer id){
        Room room = RoomDB.getRoom(id);

        if(room == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }

        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(room).build();
    }

    @POST
    @Consumes("application/xml")
    public Response createRoom(Room room){
        if(room.getName() == null){
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("Config content not found")).build();
        }else {
            return Response.status(javax.ws.rs.core.Response.Status.CREATED).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    public Response updateRoom(@PathParam("id") Integer id, Room room){
        Room dbRoom = RoomDB.getRoom(id);
        if(dbRoom == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        if(room.getName() == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(new Message("Config content not found")).build();
        }
        RoomDB.updateRoom(id, room);
        return Response.status(javax.ws.rs.core.Response.Status.OK)
                .entity(new Message("Config Updated Successfully")).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") Integer id){
        Room room = RoomDB.getRoom(id);
        if(room == null){
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        RoomDB.removeRoom(id);
        return Response.status(javax.ws.rs.core.Response.Status.OK).build();
    }
}
