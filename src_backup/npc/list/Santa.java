package npc.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import consts.ConstNpc;
import item.Item;
import managers.TopPlayer;
import managers.TopPlayerManager;
import npc.Npc;
import player.Player;
import services.ShopService;
import services.func.Input;
import services.player.InventoryService;

public class Santa extends Npc {

    public Santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {

            Item pGG = InventoryService.gI().findItem(player.inventory.itemsBag, 459);
            int soLuong = 0;
            if (pGG != null) {
                soLuong = pGG.quantity;
            }
            List<String> menu = new ArrayList<>(Arrays.asList(
                    "Cửa hàng",
                    "Mở rộng\nHành trang\nRương đồ",
                    "Nhập mã\nquà tặng",
                    "Cửa hàng\nHạn sử dụng",
                    "Tiệm\nHớt tóc",
                    "Danh\nhiệu"
            ));

            if (soLuong >= 1) {
                menu.add(1, "Giảm giá\n80%");
            }

            String[] menus = menu.toArray(String[]::new);

            createOtherMenu(player, ConstNpc.BASE_MENU,
                    "Xin chào, ta có một số vật phẩm đặc biệt cậu có muốn xem không?", menus);
        }

    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            Item pGG = InventoryService.gI().findItem(player.inventory.itemsBag, 459);
            int soLuong = 0;
            if (pGG != null) {
                soLuong = pGG.quantity;
            }

            if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                if (player.idMark.isBaseMenu()) {
                    switch (select) {
                        case 0 -> ShopService.gI().opendShop(player, "SANTA", false);
                        case 1 -> {
                            if (soLuong >= 1) {
                                ShopService.gI().opendShop(player, "SANTA_GIAM_GIA", false);
                            } else {
                                ShopService.gI().opendShop(player, "SANTA_MO_RONG_HANH_TRANG", false);
                            }
                        }
                        case 2 -> {
                            if (soLuong >= 1) {
                                ShopService.gI().opendShop(player, "SANTA_MO_RONG_HANH_TRANG", false);
                            } else {
                                Input.gI().createFormGiftCode(player);
                            }
                        }
                        case 3 -> {
                            if (soLuong >= 1) {
                                Input.gI().createFormGiftCode(player);
                            } else {
                                ShopService.gI().opendShop(player, "SANTA_HAN_SU_DUNG", false);
                            }
                        }
                        case 4 -> {
                            if (soLuong >= 1) {
                                ShopService.gI().opendShop(player, "SANTA_HAN_SU_DUNG", false);
                            } else {
                                ShopService.gI().opendShop(player, "SANTA_HEAD", false);
                            }
                        }
                        case 5 -> {
                            if (soLuong >= 1) {
                                ShopService.gI().opendShop(player, "SANTA_HEAD", false);
                            } else {
                                ShopService.gI().opendShop(player, "SANTA_DANH_HIEU", false);
                            }
                        }
                        case 6 -> ShopService.gI().opendShop(player, "SANTA_DANH_HIEU", false);
                    }
                }
            }
        }
    }

    public void traoTop(Player player) {
        // Lấy danh sách các top player
        List<TopPlayer> topBossList = TopPlayerManager.GetTopBoss();
        boolean found = false;
        String topMessage = "";

        // Kiểm tra người chơi có trong danh sách không
        for (int i = 0; i < topBossList.size(); i++) {
            TopPlayer topPlayer = topBossList.get(i);
            if (topPlayer.id == player.id) {
                // Thông báo vị trí và điểm của người chơi trong bảng xếp hạng
                topMessage = "Bạn đang đứng ở vị trí: " + (i + 1) + " trong bảng xếp hạng TOP Boss với " + topPlayer.amount + " điểm sự kiện. "
                        + "Bạn sẽ nhận được phần thưởng xứng đáng!";
                found = true;
                break;
            }
        }

        // Nếu không tìm thấy người chơi trong bảng xếp hạng
        if (!found) {
            topMessage = "Bạn không có mặt trong bảng xếp hạng TOP Boss.";
        }

        // Tạo menu với thông báo và nút "Nhận thưởng"
        this.createOtherMenu(player, 1, topMessage, "Nhận thưởng", "Đóng");
    }

    public void showTop(Player player) {
        // Lấy danh sách các top player
        List<TopPlayer> topBossList = TopPlayerManager.GetTopBoss();
        boolean found = false;
        StringBuilder topMessage = new StringBuilder();

        // Kiểm tra người chơi có trong danh sách không và tạo thông báo về vị trí
        for (int i = 0; i < topBossList.size(); i++) {
            TopPlayer topPlayer = topBossList.get(i);
            if (topPlayer.id == player.id) {
                topMessage.append("Bạn đang ở vị trí ").append(i + 1).append(" với ").append(topPlayer.amount).append(" điểm sự kiện.\n");
                found = true;
                break;
            }
        }

        // Nếu người chơi không có trong bảng xếp hạng
        if (!found) {
            topMessage.append("Bạn không có mặt trong bảng xếp hạng TOP Boss.\n");
        }

        // Hiển thị danh sách top 1 - top 10 (nếu có)
        topMessage.append("\nDanh sách TOP Boss:\n");
        int topLimit = Math.min(10, topBossList.size()); // Giới hạn hiển thị tối đa 10 người
        for (int i = 0; i < topLimit; i++) {
            TopPlayer topPlayer = topBossList.get(i);
            topMessage.append(i + 1).append(". ").append(topPlayer.name).append(" - ").append(topPlayer.amount).append(" điểm sự kiện\n");
        }

        // Tạo menu hiển thị thông báo và danh sách top
        this.createOtherMenu(player, 3, topMessage.toString(), "Đóng");
    }

}

