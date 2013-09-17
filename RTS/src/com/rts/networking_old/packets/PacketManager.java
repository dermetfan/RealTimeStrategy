package com.rts.networking_old.packets;

import com.rts.networking_old.packets.game.MoveEntityPacket;
import com.rts.networking_old.packets.system.ChatPacket;
import com.rts.networking_old.packets.game.EntityCreationPacket;
import com.rts.networking_old.packets.system.DisconnectPacket;
import com.rts.networking_old.packets.system.PingPacket;
import com.rts.networking_old.packets.game.RequestEntityPacket;
import com.rts.util.Logger;

import java.util.HashMap;

public class PacketManager {
    /* The packet id to assign to packets. Will increment after each use */
    private static int packetId = 1;
    /* Simple hashMap to obtain an ID from a packet object */
    private static HashMap<Class<? extends Packet>, Integer> packetToId = new HashMap<Class<? extends Packet>, Integer>();
    /* Simple hashMap to obtain an empty object from ID */
    private static HashMap<Integer, Class<? extends Packet>> idToPacket = new HashMap<Integer, Class<? extends Packet>>();

    /**
     * All packets should really be going here. This sets up the hashmaps so you
     * are able to obtain the ID's.
     */
    static {
        registerPacket(new PingPacket());
        registerPacket(new DisconnectPacket());
        registerPacket(new ChatPacket());
        registerPacket(new EntityCreationPacket());
        registerPacket(new RequestEntityPacket());
        registerPacket(new MoveEntityPacket());
    }

    /**
     * Call this function to make sure the static gets used.
     */
    public static void empty() {

    }

    /**
     * This class registers all the packets and puts them in the hash maps.
     *
     * @param packet The packet to register.
     */
    public static void registerPacket(Packet packet) {
        if (!packetToId.containsKey(packet.getClass())) {
            Logger.getInstance().system("Registered packet: " + packet.getClass().getSimpleName() + " with id: " + packetId);
            packetToId.put(packet.getClass(), packetId);
            idToPacket.put(packetId, packet.getClass());
            packetId++;
        }
    }

    /**
     * This is a static function so you can obtain the id from a packet.
     *
     * @param packet The packet to get the id for.
     * @return The packet id.
     */
    public static int getPacketId(Packet packet) {
        return packetToId.get(packet.getClass());
    }

    /**
     * This is a static function so you can obtain a new object from an ID.
     *
     * @param id The id you want the object from.
     * @return The object (packet)
     */
    public static Class<? extends Packet> getPacket(int id) {
        return idToPacket.get(id);
    }
}