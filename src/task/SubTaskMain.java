package task;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class SubTaskMain {

    public short count;

    public String name;

    public short maxCount;

    public String notify;

    public byte npcId;

    public short mapId;

    public SubTaskMain() {
    }

    public SubTaskMain(SubTaskMain stm) {
        this.count = 0;
        this.name = stm.name;
        this.maxCount = stm.maxCount;
        this.npcId = stm.npcId;
        this.mapId = stm.mapId;
        this.notify = stm.notify;
    }

}

