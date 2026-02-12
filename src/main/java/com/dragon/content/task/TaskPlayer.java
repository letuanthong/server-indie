package com.dragon.content.task;

/*
 * @Author: dev1sme
 * @Description: Ngọc Rồng - Server Chuẩn Teamobi 
 * @Collab: ???
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
