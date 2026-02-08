package task;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class TaskPlayer {

    public TaskMain taskMain;

    public SideTask sideTask;

    public ClanTask clanTask;

    public TaskPlayer() {
        this.sideTask = new SideTask();
        this.clanTask = new ClanTask();
    }

    public void dispose() {
        this.taskMain = null;
        this.sideTask = null;
        this.clanTask = null;
    }

}

