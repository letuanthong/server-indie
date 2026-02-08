package managers.boss;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class LunarNewYearEventManager extends BossManager {

    private static LunarNewYearEventManager instance;

    public static LunarNewYearEventManager gI() {
        if (instance == null) {
            instance = new LunarNewYearEventManager();
        }
        return instance;
    }

}

