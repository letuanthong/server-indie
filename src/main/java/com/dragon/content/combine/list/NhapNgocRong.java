package com.dragon.content.combine.list;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


import com.dragon.consts.ConstNpc;
import com.dragon.model.item.Item;
import com.dragon.content.combine.CombineService;
import com.dragon.model.player.Player;
import com.dragon.services.player.InventoryService;
import com.dragon.services.ItemService;

public class NhapNgocRong {

    public static void showInfoCombine(Player player) {
        if (InventoryService.gI().getCountEmptyBag(player) > 0) {
            if (player.combineNew.itemsCombine.size() == 1) {
                Item item = player.combineNew.itemsCombine.get(0);
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    String npcSay = "|2|Con có muốn biến 7 " + item.template.name + " thành\n"
                            + "1 viên " + ItemService.gI().getTemplate((short) (item.template.id - 1)).name + "\n"
                            + "|7|Cần 7 " + item.template.name;
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép", "Từ chối");
                } else {
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                }
            } else {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
            }
        } else {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
        }
    }

    public static void nhapNgocRong(Player player) {
        if (InventoryService.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    CombineService.gI().sendEffectCombineDB(player, item.template.iconID);
                    Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                    InventoryService.gI().addItemBag(player, nr);
                    InventoryService.gI().subQuantityItemsBag(player, item, 7);
                    InventoryService.gI().sendItemBags(player);
                    CombineService.gI().reOpenItemCombine(player);
                }
            }
        }
    }

}

