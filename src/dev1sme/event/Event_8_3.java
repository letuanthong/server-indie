/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev1sme.event;

// import boss.BossData;
// import consts.ConstItem;
// import dev1sme.event.Event;
// import player.Inventory;
// import utils.Logger;
// import utils.Util;
import java.util.List;

import consts.ConstMap;
import consts.ConstNpc;
import item.Item;
import item.Item.ItemOption;
import map.ItemMap;
import map.Map;
import map.Zone;
import mob.Mob;
import npc.Npc;
import player.Player;
import server.Manager;
import services.ItemService;
import services.Service;
import services.map.MapService;
import services.player.InventoryService;

public class Event_8_3 extends Event {

    public Zone zone;
    private int uniqueID;

    @Override
    public void init() {

        uniqueID = -99999;
        initNpc();
    }

    private long generateUniqueID() {
        return uniqueID++;
    }

    @Override
    public void initNpc() {
        Map map = MapService.gI().getMapById(ConstMap.DAO_KAME);

        if (map != null && Manager.EVENT_SEVER == 1) {
            Npc npc;
            npc = new Npc(map.mapId, 1, 273, 288, ConstNpc.EVENT, 9976) {
                @Override
                public void openBaseMenu(Player player) {
                    if (canOpenNpc(player)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin Chào Tôi Là ChiChi. Bạn Có Muốn Đổi  Hoa Lấy ","Đổi Điểm\nSự Kiện", "Từ chối");
                    }
                }

                @Override
                public void confirmMenu(Player player, int select) {
                    int menuID = player.idMark.getIndexMenu();
                    if (player.idMark.isBaseMenu()) {
                        switch (select) {
                            case 0 -> this.createOtherMenu(player, ConstNpc.ORTHER_MENU, "Xin chào, sự kiện hè 2023 đang được diễn ra", "Đổi\n Vỏ Ốc", "Đổi Sò", "Đổi Cua", "Đổi \nSao Biển", "Đổi Quà\n Đặc Biệt", "Từ Chối");
                        }
                    } else if (menuID == ConstNpc.ORTHER_MENU) {
                        if (select < 3) {
                            switch (select) {
                                case 0 -> {
                                    if (InventoryService.gI().getCountEmptyBag(player) > 0) {
                                        int evPoint = player.event.getEventPoint();
                                        if (evPoint >= 999) {
                                            Item capsule = ItemService.gI().createNewItem((short) 2052, 1);
                                            player.event.setEventPoint(evPoint - 999);
                                            
                                            capsule.itemOptions.add(new ItemOption(74, 0));
                                            capsule.itemOptions.add(new ItemOption(30, 0));
                                            InventoryService.gI().addItemBag(player, capsule);
                                            InventoryService.gI().sendItemBags(player);
                                            Service.gI().sendThongBao(player, "Bạn nhận được Capsule Hồng");
                                        } else {
                                            Service.gI().sendThongBao(player, "Cần 999 điểm tích lũy để đổi");
                                        }
                                    } else {
                                        Service.gI().sendThongBao(player, "Hành trang đầy.");
                                    }
                                }
                                default -> {
                                    int n = 0;
                                    switch (select) {
                                        case 0 -> n = 1;
                                        case 1 -> n = 10;
                                        case 2 -> n = 99;
                                    }
                                    
                                    if (n > 0) {
                                        Item bonghoa = InventoryService.gI().finditemBongHoa(player, n);
                                        if (bonghoa != null) {
                                            int evPoint = player.event.getEventPoint();
                                            player.event.setEventPoint(evPoint + n);
                                            InventoryService.gI().subQuantityItemsBag(player, bonghoa, n);
                                            Service.gI().sendThongBao(player, "Bạn nhận được " + n + " điểm sự kiện");
                                        } else {
                                            Service.gI().sendThongBao(player,
                                                    "Cần ít nhất " + n + " bông hoa để có thể tặng");
                                        }
                                    } else {
                                        Service.gI().sendThongBao(player, "Cần ít nhất " + n + " bông hoa để có thể tặng");
                                    }
                                }
                            }

                        }
                    }
                }
            };
            map.addNpc(npc);
        }
    }

    @Override
    public void initMap() {

    }

    @Override
    public void dropItem(Player player, Mob mob, List<ItemMap> list, int x, int yEnd) {
        int mapid = player.zone.map.mapId;
    }

    @Override
    public boolean useItem(Player player, Item item) {

        return false;

    }

}

