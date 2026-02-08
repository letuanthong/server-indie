package services.map;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


import consts.ConstNpc;
import item.Item;
import npc.Npc;
import npc.NpcFactory;
import player.Player;
import server.Manager;
import network.Message;
import services.ItemService;
import services.ItemTimeService;
import services.Service;
import services.player.InventoryService;
import services.shenron.SummonDragon;
import utils.Logger;

public class NpcService {

    private static NpcService i;

    public static NpcService gI() {
        if (i == null) {
            i = new NpcService();
        }
        return i;
    }

    public void createMenuRongThieng(Player player, int indexMenu, String npcSay, String... menuSelect) {
        createMenu(player, indexMenu, ConstNpc.RONG_THIENG, 0, npcSay, menuSelect);
    }

    public void createMenuConMeo(Player player, int indexMenu, int avatar, String npcSay, String... menuSelect) {
        createMenu(player, indexMenu, ConstNpc.CON_MEO, avatar, npcSay, menuSelect);
    }

    public void createMenuConMeo(Player player, int indexMenu, int avatar, String npcSay, String[] menuSelect, Object object) {
        NpcFactory.PLAYERID_OBJECT.put(player.id, object);
        createMenuConMeo(player, indexMenu, avatar, npcSay, menuSelect);
    }

    private void createMenu(Player player, int indexMenu, byte npcTempId, int avatar, String npcSay, String... menuSelect) {
        if (player == null || !player.isPl()) {
            return;
        }
        Message msg;
        try {
            player.idMark.setIndexMenu(indexMenu);
            msg = new Message(32);
            msg.writer().writeShort(npcTempId);
            msg.writer().writeUTF(npcSay);
            msg.writer().writeByte(menuSelect.length);
            for (String menu : menuSelect) {
                msg.writer().writeUTF(menu);
            }
            if (avatar != -1) {
                msg.writer().writeShort(avatar);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(NpcService.class, e);
        }
    }
public boolean SummonDragonWhis_1_1(Player playerSummonShenron, int select) {
        switch (select) {
            case 0:           
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 1:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 2: 
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 3:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 4:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
        }
        return true;
    }

    public boolean SummonDragonWhis_1_2(Player playerSummonShenron, int select) {
        switch (select) {
            case 0:
                 Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            break;
            case 1:
              Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            break;
            case 2:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
        }
        return true;
    }

    public boolean SummonDragonWhis_2_1(Player playerSummonShenron, int select) {
        switch (select) {
            case 0: 
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 1: 
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 2:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
        }
        return true;
    }

    public boolean SummonDragonWhis_3_1(Player playerSummonShenron, int select) {
        switch (select) {
            case 0:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 1:  
                Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 2:
                   Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
        }
        return true;
    }

    public boolean SummonDragonBlack_1(Player playerSummonShenron, int select) {
        switch (select) {
            case 0:
                Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            
                break;
            case 1:
                Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            
                break;
            case 2:
                Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            
                break;
            case 3:
               Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            
                break;
            case 4:
                Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            
                break;
        }
        return true;
    }

    public boolean SummonDragonICE_1(Player playerSummonShenron, int select) {
        switch (select) {
            case 0: {
               Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            }
            break;
            case 1: {
              Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
            }
            break;
            case 2:
                  Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            case 3: {
                  Service.gI().sendThongBao(playerSummonShenron, "chua lam:v luoi qua huhu");
                break;
            }
        }
        return true;
    }

    public void createTutorial(Player player, int avatar, String npcSay) {
        Message msg;
        try {
            msg = new Message(38);
            msg.writer().writeShort(ConstNpc.CON_MEO);
            msg.writer().writeUTF(npcSay);
            if (avatar != -1) {
                msg.writer().writeShort(avatar);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createTutorial(Player player, int tempId, int avatar, String npcSay) {
        Message msg;
        try {
            msg = new Message(38);
            msg.writer().writeShort(tempId);
            msg.writer().writeUTF(npcSay);
            if (avatar != -1) {
                msg.writer().writeShort(avatar);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public int getAvatar(int npcId) {
        for (Npc npc : Manager.NPCS) {
            if (npc.tempId == npcId) {
                return npc.avartar;
            }
        }
        return 1139;
    }
}

