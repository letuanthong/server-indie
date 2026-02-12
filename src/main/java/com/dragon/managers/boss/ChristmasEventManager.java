package com.dragon.managers.boss;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


public class ChristmasEventManager extends BossManager {

    private static ChristmasEventManager instance;

    public static ChristmasEventManager gI() {
        if (instance == null) {
            instance = new ChristmasEventManager();
        }
        return instance;
    }

}

