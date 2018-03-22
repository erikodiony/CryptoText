package tk.ngezz.cryptotext;

import java.nio.charset.StandardCharsets;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class Converter_Unicode {

    //Signed Byte == -128 s/d 127
    //Unsigned Byte == 0 - 255

    public static int[] encode(String txt) {
        byte[] signed = txt.getBytes(StandardCharsets.UTF_8);
        return toUnsigned(signed);
    }

    public static String decode(int[] txt) {
        byte[] res = toSigned(txt);
        return new String(res, StandardCharsets.UTF_8);
    }

    public static int[] toUnsigned(byte[] signedByte) {
        int[] res = new int[signedByte.length];
        for (int i = 0; i < signedByte.length; i++) {
            res[i] = signedByte[i] & 0xFF;
        }
        return res;
    }

    public static byte[] toSigned(int[] unsignedInt) {
        byte[] res = new byte[unsignedInt.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = (byte)unsignedInt[i];
        }
        return res;
    }

}
