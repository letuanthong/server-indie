package npc.list;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import managers.boss.BossManager;
import consts.ConstNpc;
import npc.Npc;
import player.Player;
import services.map.NpcService;
import services.Service;
import utils.Util;

public class Potage extends Npc {

    public Potage(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (this.mapId == 140) {
                Player BossClone = BossManager.gI().findBossClone(player);
                if (BossClone != null) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Đang có 1 nhân bản của " + BossClone.name + " hãy chờ kết quả trận đấu",
                            "OK");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hãy giúp ta đánh bại bản sao\nNgươi chỉ có 5 phút để hạ hắn\nPhần thưởng cho ngươi là 1 bình Commeson",
                            "Hướng\ndẫn\nthêm", "OK", "Từ chối");
                }
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            if (this.mapId == 140) {
                if (player.idMark.isBaseMenu()) {
                    Player BossClone = BossManager.gI().findBossClone(player);
                    if (BossClone == null) {
                        switch (select) {
                            case 0 ->
                                NpcService.gI().createTutorial(player, tempId, this.avartar, "Thứ bị phong ấn tại đây là vũ khí có tên Commesonđược tạo ra nhằm bảo vệ cho hành tinh PotaufeuTuy nhiên nó đã tàn phá mọi thứ trong quá khứ\nKhiến cư dân Potaufeu niêm phong nó với cái giáphải trả là mạng sống của họTa, Potage là người duy nhất sống sótvà ta đã bảo vệ phong ấn hơn một trăm năm.\nTuy nhiên bọn xâm lượt Gryll đã đến và giải thoát CommesonHãy giúp ta tiêu diệt bản sao do Commeson tạo ravà niêm phong Commeson một lần và mãi mãi");
                            case 1 -> {
                                if (!Util.isAfterMidnight(player.lastPkCommesonTime) && !player.isAdmin()) {
                                    Service.gI().sendThongBao(player, "Hãy chờ đến ngày mai");
                                } else {
                                    Service.gI().callNhanBan(player);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

