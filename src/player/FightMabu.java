package player;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import services.Service;
import services.dungeon.MajinBuuService;
import services.map.MapService;
import utils.Util;

public class FightMabu {

    public final byte POINT_MAX = 10;

    public int pointMabu = 0;
    public int pointPercent = 0;
    private final Player player;

    public FightMabu(Player player) {
        this.player = player;
    }

    public void changePoint(byte pointAdd) {
        if (MapService.gI().isMapMaBu(player.zone.map.mapId)) {
            pointMabu += pointAdd;
            pointPercent = 0;
            Service.gI().SendPowerInfo(player);
            if (pointMabu >= POINT_MAX && player.zone.map.mapId != 120) {
                MajinBuuService.gI().xuongTangDuoi(player);
            }
        }
    }

    public void changePercentPoint(byte pointAdd) {
        if (MapService.gI().isMapMaBu(player.zone.map.mapId)) {
            pointPercent += pointAdd;
            if (pointPercent > 100) {
                pointPercent /= Util.nextInt(2, 5);
            }
            Service.gI().SendPercentPowerInfo(player);
        }
    }

    public void clear() {
        this.pointMabu = 0;
    }
}

