package tk.ngezz.cryptotext;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class RUNNER_EXEC {

    //To create Directory if CryptoText dir is not exist !
    public static void createDir() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/CryptoText");
        if (!folder.exists()) {
            if (folder.mkdir() == true) { } //Trigger to Creating Folder "CryptoText"
        }
    }

    //Read Text from OpenFileDialog to value of EditText !
    public static String readFile(Uri uri, Context cx){
        ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
        try {
            InputStream is_TXT = cx.getContentResolver().openInputStream(uri);
            int i = is_TXT.read();
            while (i != -1) {
                byteAOS.write(i);
                i = is_TXT.read();
            }
            is_TXT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteAOS.toString();
    }

    //Checker Input of Encrypt Text ! (Menu Decrypt)
    public static boolean checkerCharacter(String[] msg_Str) {
        int[] msg = new int[msg_Str.length];
        try {
            for (int i = 0; i < msg_Str.length; i++) {
                msg[i] = Integer.parseInt(msg_Str[i]);
            }
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    //MAIN FUNCTION (Encrypt)
    public static String encrypt(int[] key_param, int[] msg) {
        int[] msg_rot13 = null;
        int[] msg_elgamal = null;

        switch (key_param[0]) {
            case 0:
                msg_rot13 = msg;
                break;
            case 1:
                if (key_param[1] == 0) {
                    msg_rot13 = Algorithm_ROT13.encrypt_FWD(msg);
                } else {
                    msg_rot13 = Algorithm_ROT13.encrypt_REV(msg);
                }
                break;
        }

        switch (key_param[2]) {
            case 0:
                msg_elgamal = msg_rot13;
                break;
            case 1:
                msg_elgamal = Algorithm_ELGAMAL.encrypt(msg_rot13,key_param[3],key_param[4]);
                break;
        }

        StringBuilder builder = new StringBuilder();
        for (int x: msg_elgamal) {
            builder.append(x);
            builder.append("-");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();

    }

    //MAIN FUNCTION (Decrypt)
    public static String decrypt(int[] key_param, String[] msg_Str) {
        int[] msg = new int[msg_Str.length];

        for (int i = 0; i < msg_Str.length; i++) {
            msg[i] = Integer.parseInt(msg_Str[i]);
        }

        int[] msg_elgamal = null;
        int[] msg_rot13 = null;

        switch (key_param[2]) {
            case 0:
                msg_elgamal = msg;
                break;
            case 1:
                msg_elgamal = Algorithm_ELGAMAL.decrypt(msg,key_param[3],key_param[4]);
                break;
        }

        switch (key_param[0]) {
            case 0:
                msg_rot13 = msg_elgamal;
                break;
            case 1:
                if (key_param[1] == 0) {
                    msg_rot13 = Algorithm_ROT13.decrypt_FWD(msg_elgamal);
                } else {
                    msg_rot13 = Algorithm_ROT13.decrypt_REV(msg_elgamal);
                }
                break;
        }

        return Converter_Unicode.decode(msg_rot13);

    }


}