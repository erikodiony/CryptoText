package tk.ngezz.cryptotext;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class Builder_Key {

    public static int[] key_param;

    public static void writeKey(Context cx, int[] val_ROT13, int[] val_ElGamal) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        String date = df.format(Calendar.getInstance().getTime());
        JSONObject obj = new JSONObject();
        try {
            obj.put("Param1",val_ROT13[0]);
            obj.put("Param2",val_ROT13[1]);
            obj.put("Param3",val_ElGamal[0]);
            obj.put("Param4",val_ElGamal[1]);
            obj.put("Param5",val_ElGamal[2]);
            try {
                FileWriter fw = new FileWriter(Environment.getExternalStorageDirectory() + "/CryptoText/" + "Key__" + date + ".crtx");
                fw.write(obj.toString());
                fw.flush();
                fw.close();
                Toast.makeText(cx,"Key__" + date + ".crtx" + " was created on CryptoText Folder . . .", Toast.LENGTH_LONG).show();
            }
            catch (IOException e) {
                Toast.makeText(cx,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e) {
            Toast.makeText(cx,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean check_Key (Uri uri, Context cx) {
        if (!readKey(uri,cx)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean readKey (Uri uri, Context cx) {
        String json = getFile(uri, cx);
        int param1;
        int param2;
        int param3;
        int param4;
        int param5;

        try {
            JSONObject obj = new JSONObject(json);
            param1 = obj.getInt("Param1");
            param2 = obj.getInt("Param2");
            param3 = obj.getInt("Param3");
            param4 = obj.getInt("Param4");
            param5 = obj.getInt("Param5");

            key_param = new int[] {param1,param2,param3,param4,param5};

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getFile(Uri uri, Context cx) {
        String json = null;
        try {
            InputStream is = cx.getContentResolver().openInputStream(uri);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
