package clan;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class ClanMessage {

    public static final byte BLACK = 0;
    public static final byte RED = 1;

    private Clan clan;

    public int id;

    public byte type;

    public int playerId;

    public String playerName;

    public long playerPower;

    public byte role;

    public int time;

    public String text;

    public byte receiveDonate;

    public byte maxDonate;

    public byte isNewMessage;

    public byte color;

    public ClanMessage(Clan clan) {
        this.clan = clan;
        this.id = clan.clanMessageId++;
        this.isNewMessage = 1;
        this.time = (int) (System.currentTimeMillis() / 1000 - 1000000000);
    }

    public void dispose() {
        this.clan = null;
        this.text = null;
    }
}

