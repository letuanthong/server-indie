package com.dragon.model.player.badges;

import com.dragon.model.player.Player;

public class BadgesService {

    public static void turnOnBadges(Player player, int id) {
        if (player.dataBadges != null) {
            for (BadgesData data : player.dataBadges) {
                data.isUse = (data.idBadGes == id);
            }
        }
    }

}

