package com.rts.networking_old.host;

import com.rts.networking_old.AbstractServerClient;
import com.rts.networking_old.NetworkEntity;
import com.rts.networking_old.packets.Packet;
import com.rts.networking_old.packets.game.MoveEntityPacket;
import com.rts.networking_old.packets.system.ChatPacket;
import com.rts.networking_old.packets.game.EntityCreationPacket;
import com.rts.networking_old.packets.system.DisconnectPacket;
import com.rts.networking_old.packets.system.PingPacket;
import com.rts.networking_old.packets.game.RequestEntityPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/12/13
 * Time: 2:00 AM
 * To change this template use File | Settings | File Templates.
 */

public class ServerClient extends AbstractServerClient {
    /**
     * The main constructor for the server client. This will set everything up, disable Naggle's tcp algorithm
     * and start the thread.
     *
     * @param socket  the socket this serverclient connected trough
     * @param timeOut the max duration between packets before the client will get kicked
     * @param id      id identifying the serverclient
     * @throws java.io.IOException when the socket is null
     */
    public ServerClient(Socket socket, int timeOut, int id) throws IOException {
        super(socket, timeOut, id);
    }

    /**
     * This function will get executed if a player disconnects or times out.
     *
     * @param reason the reason the player disconnected {DisconnectPacket.java}
     */
    @Override
    protected void disconnect(int reason) {
        Server.getServer().removeClient(this);  //TODO send notification to everybody
        Server.getServer().sendAllTCP(new DisconnectPacket(reason, id));
    }

    @Override
    protected void processPacket(Packet packet) {
        if (packet instanceof PingPacket) {
            writePacket(packet);
        } else if (packet instanceof RequestEntityPacket) {
            NetworkEntity networkEntity = new NetworkEntity(ServerGameManager.getId(), (RequestEntityPacket) packet);
            EntityCreationPacket entityCreationPacket = new EntityCreationPacket(-1, networkEntity);
            Server.getServer().sendAllTCP(entityCreationPacket);
            // TODO check if you can actually build it
            // TODO check for resources
            // Todo send a new packet to all
        } else {
            if (packet instanceof MoveEntityPacket) {
                // if (packet.getConnectionId() == ((MoveEntityPacket) packet).getEntityId()) {
                // }
                // TODO validate
            } else if (packet instanceof ChatPacket) {
                logger.debug("chatpack: " + ((ChatPacket) packet).getText());
            }
            Server.getServer().sendAllTCP(packet);
        }
    }
}