package npc.list;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */

import npc.Npc;
import player.Player;
import services.Service;
import services.map.ChangeMapService;
import utils.TimeUtil;
import utils.Util;

public class Tapion extends Npc {

    public Tapion(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

   
    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (mapId == 19) {
                this.createOtherMenu(player, 0, "Ác quỷ truyền thuyết Hirudegarn\nđã thoát khỏi phong ấn ngàn năm\nHãy giúp tôi chế ngự nó", "OK", "Từ chối");
            } else if (mapId == 126) {
                this.createOtherMenu(player, 0, "Tôi sẽ đưa bạn về", "OK", "Từ chối");
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            if (!TimeUtil.is21H()) {
                Service.gI().sendThongBao(player, "Bạn chỉ có thể vào bản đồ sau 22h");
                return;
            }
            switch (select) {
                case 0 -> {
                    if (mapId == 19) {
                        ChangeMapService.gI().changeMapNonSpaceship(player, 126, 200 + Util.nextInt(-100, 100), 360);
                    } else if (mapId == 126) {
                        ChangeMapService.gI().changeMapNonSpaceship(player, 19, 1000 + Util.nextInt(-100, 100), 360);
                    }
                }
            }
        }
    }
}

