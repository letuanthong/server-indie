package interfaces;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public interface IEvent {

    void init();

    void npc();

    void createNpc(int mapId, int npcId, int x, int y);

    void boss();

    void createBoss(int bossId, int... total);

    void itemMap();

    void itemBoss();
}

