package npc.list;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */
import consts.ConstNpc;
import npc.Npc;
import player.Player;
import services.Service;

public class GiuMaDauBo extends Npc {

    public GiuMaDauBo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ngươi đang muốn tìm mảnh vỡ và mảnh hồn bông tai Porata trong truyền thuyết, ta sẽ đưa ngươi đến đó ?",
                    "Khiêu chiến\nBoss", "Điểm danh\n+1 Capsule\nBang", "OK", "Đóng");
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            switch (select) {
                case 0 -> {
                }
                case 2 -> {
                    if (player.nPoint.power < 80_000_000_000L) {
                        Service.gI().sendThongBao(player, "KHÔNG ĐỦ SỨC MẠNH");
                    } else {
                        player.type = 5;
                        player.maxTime = 5;
                        Service.gI().Transport(player);
                    }
                }
                default -> {

                }
            }
        }
    }
}
