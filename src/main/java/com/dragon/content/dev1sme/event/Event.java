/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dragon.content.dev1sme.event;

import java.util.List;

import com.dragon.model.item.Item;
import com.dragon.model.map.ItemMap;
import com.dragon.model.mob.Mob;
import com.dragon.model.player.Player;
import com.dragon.utils.Logger;

public abstract class Event {

    private static Event instance;

    public static Event getInstance() {
        return instance;
    }

    public static void initEvent(String event) {
        if (event != null) {
            try {
                instance = (Event) Class.forName(event).getDeclaredConstructor().newInstance();
                Logger.success("[DEV1SME] - Event Active: " + event + "\n");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                     NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
                Logger.logException(Event.class, e);
            }
        }
    }

    public static boolean isEvent() {
        return instance != null;
    }

    public abstract void init();
    public abstract void initNpc();
    public abstract void initMap();
    public abstract void dropItem(Player player, Mob mob, List<ItemMap> list, int x, int yEnd);
    public abstract boolean useItem(Player player, Item item);
}

