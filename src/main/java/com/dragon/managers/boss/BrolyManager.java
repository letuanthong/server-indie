package com.dragon.managers.boss;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
 */


public class BrolyManager extends BossManager {

    private static BrolyManager instance;

    public static BrolyManager gI() {
        if (instance == null) {
            instance = new BrolyManager();
        }
        return instance;
    }

}

