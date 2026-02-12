package com.dragon.services.func;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import java.io.IOException;

import com.dragon.model.map.Zone;
import com.dragon.core.network.Message;
import com.dragon.model.player.Player;
import com.dragon.services.Service;
import com.dragon.utils.Logger;

public class EffectMapService {

    private static EffectMapService i;

    private EffectMapService() {

    }

    public static EffectMapService gI() {
        if (i == null) {
            i = new EffectMapService();
        }
        return i;
    }

    public void sendEffectMapToPlayer(Player player, int id, int layer, int loop, int x, int y, int delay) {
        Message msg = null;
        try {
            msg = new Message(113);
            msg.writer().writeByte(id);
            msg.writer().writeByte(layer);
            msg.writer().writeByte(id);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            msg.writer().writeShort(delay);
            player.sendMessage(msg);
        } catch (IOException e) {
            Logger.error("EffectMapService Lỗi gửi hiệu ứng bản đồ đến người chơi: " + e.getMessage());
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public void sendEffectMapToAllInMap(Zone zone, int id, int layer, int loop, int x, int y, int delay) {
        Message msg = null;
        try {
            msg = new Message(113);
            msg.writer().writeByte(loop);
            msg.writer().writeByte(layer);
            msg.writer().writeByte(id);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            msg.writer().writeShort(delay);
            Service.gI().sendMessAllPlayerInMap(zone, msg);
        } catch (IOException e) {
            Logger.error("EffectMapService Lỗi gửi hiệu ứng bản đồ đến tất cả người chơi trong bản đồ: " + e.getMessage());
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public void sendEffectMapToAllInMap(Player player, int id, int layer, int loop, int x, int y, int delay) {
        Message msg = null;
        try {
            msg = new Message(113);
            msg.writer().writeByte(loop);
            msg.writer().writeByte(layer);
            msg.writer().writeByte(id);
            msg.writer().writeShort(x);
            msg.writer().writeShort(y);
            msg.writer().writeShort(delay);
            Service.gI().sendMessAllPlayerInMap(player, msg);
        } catch (IOException e) {
            Logger.error("EffectMapService Lỗi gửi hiệu ứng bản đồ đến tất cả người chơi trong bản đồ: " + e.getMessage());
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

}

