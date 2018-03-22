package tk.ngezz.cryptotext;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class Algorithm_ROT13 {

    public static int[] encrypt_FWD (int[] msg) {
        int[] res = new int[msg.length];
        for (int i = 0; i < msg.length; i++) {
            if ((msg[i] + 13) > 255) {
                res[i] = (msg[i] + 13) - 256;
            } else {
                res[i] = msg[i] + 13;
            }
        }
        return res;
    }

    public static int[] encrypt_REV (int[] msg) {
        int[] res = new int[msg.length];
        for (int i = 0; i < msg.length; i++) {
            if ((msg[i] - 13) < 0) {
                res[i] = (msg[i] - 13) + 256;
            } else {
                res[i] = msg[i] - 13;
            }
        }
        return res;
    }

    public static int[] decrypt_FWD (int[] msg) {
        return encrypt_REV(msg);
    }

    public static int[] decrypt_REV (int[] msg) {
        return encrypt_FWD(msg);
    }

}

