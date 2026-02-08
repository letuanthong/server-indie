package managers.boss;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class HungVuongEventManager extends BossManager {

    private static HungVuongEventManager instance;

    public static HungVuongEventManager gI() {
        if (instance == null) {
            instance = new HungVuongEventManager();
        }
        return instance;
    }

}

