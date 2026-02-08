package player.badges;

import player.Player;

public class BadgesService {

    public static void turnOnBadges(Player player, int id) {
        if (player.dataBadges != null) {
            for (BadgesData data : player.dataBadges) {
                data.isUse = (data.idBadGes == id);
            }
        }
    }

}

