package radar;

/*
 * @Author: NgocRongWhis
 * @Description: Ngọc Rồng Whis - Máy Chủ Chuẩn Teamobi 2024
 * @Group Zalo: https://zalo.me/g/qabzvn331
 */


public class OptionCard {

    public int id;
    public int param;
    public byte active;

    public OptionCard(int i, int p, byte a) {
        id = i;
        param = p;
        active = a;
    }

    @Override
    public String toString() {
        final String n = "\"";
        return "{"
                + n + "id" + n + ":" + n + id + n + ","
                + n + "active" + n + ":" + n + active + n + ","
                + n + "param" + n + ":" + n + param + n
                + "}";
    }
}

