package tk.ngezz.cryptotext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class Algorithm_ELGAMAL {

    //MAIN FUNCTION

    public static int[] encrypt(int[] msg, int priv_key, int pub_key) {
        int nilai_P = pub_key;
        int nilai_X = priv_key;
        int nilai_G;
        int nilai_Y;
        int[] nilai_K;
        int[] hasil_gamma;
        int[] hasil_delta;
        int[] hasil_enkripsi;

        nilai_G = GetNilai_G(nilai_P);
        nilai_Y = GetNilai_Y(nilai_P,nilai_G,nilai_X);
        nilai_K = GetNilai_K(msg,nilai_P);

        hasil_gamma = GetNilai_Gamma(nilai_G, nilai_K, nilai_P);
        hasil_delta = GetNilai_Delta(nilai_Y, nilai_K, nilai_P, msg);
        hasil_enkripsi = Gabungan_NilaiGamma_NilaiDelta(hasil_gamma, hasil_delta);

        return hasil_enkripsi;

        //https://blogermencobasukses.wordpress.com/2013/05/27/kriptografi-elgamal-part-enkripsi/
    }

    public static int[] decrypt(int[] msg, int priv_key, int pub_key) {
        int nilaiP = pub_key;
        int nilaiX = priv_key;
        int[] hasil_dekripsi;

        Split_Cipher_Gamma_Delta(msg);
        hasil_dekripsi = Proses_Get_Message(gamma_cipher, delta_cipher, nilaiP, nilaiX);

        return hasil_dekripsi;

        //https://blogermencobasukses.wordpress.com/2013/05/28/kriptografi-elgamal-part-dekripsi/
    }


    //Encrypt Function

    public static int GetNilai_G(int p) {
        int min = 1;
        int max = (p-1);
        int range = max - min + 1;

        Random rn = new Random();
        return rn.nextInt(range) + min;

    }

    public static int GetNilai_Y(int p, int g, int x) {
        BigInteger BigInt_G, BigInt_P;
        BigInteger exponent = new BigInteger(Integer.toString(x));
        BigInt_G = new BigInteger(Integer.toString(g));
        BigInt_P = new BigInteger(Integer.toString(p)); //Modulus

        return BigInt_G.modPow(exponent,BigInt_P).intValue();
    }

    public static int[] GetNilai_K(int[] msg, int p) {
        int[] val = new int[msg.length];
        int min = 1;
        int max = p - 2;
        int range = max - min + 1;

        Random rd = new Random();
        for (int i = 0; i < msg.length; i++)
        {
            int rand = rd.nextInt(range) + min;
            val[i] = rand;
        }
        return val;
    }

    public static int[] GetNilai_Gamma(int g, int[] k, int p) {
        int[] val = new int[k.length];

        BigInteger BigInt_G;
        BigInteger BigInt_P;
        BigInteger BigInt_K;

        BigInt_P = BigInteger.valueOf(p);
        BigInt_G = BigInteger.valueOf(g);

        for (int i = 0; i < k.length; i++)
        {
            BigInt_K = BigInteger.valueOf(k[i]);
            val[i] = BigInt_G.modPow(BigInt_K,BigInt_P).intValue();
        }
        return val;
    }

    public static int[] GetNilai_Delta(int y, int[] k, int p, int[] msg) {
        int[] val = new int[msg.length];
        BigInteger BigInt_Y;
        BigInteger Int_Y_pow_K;

        BigInt_Y = BigInteger.valueOf(y);
        for (int i = 0; i < msg.length; i++)
        {
            Int_Y_pow_K = BigInt_Y.pow(k[i]);
            val[i] = Int_Y_pow_K.multiply(BigInteger.valueOf(msg[i])).remainder(BigInteger.valueOf(p)).intValue();
        }
        return val;
    }

    public static int[] Gabungan_NilaiGamma_NilaiDelta(int[] gamma, int[] delta) {
        int[] val = new int[gamma.length + delta.length];
        int count = 0;
        for (int i = 0; i < gamma.length; i++)
        {
            val[count] = gamma[i];
            val[++count] = delta[i];
            ++count;
        }

        return val;
    }


    //Decrypt Function

    public static ArrayList<Integer> gamma_cipher = new ArrayList<>();
    public static ArrayList<Integer> delta_cipher = new ArrayList<>();

    public static void Split_Cipher_Gamma_Delta(int[] msg) {

        gamma_cipher.clear();
        delta_cipher.clear();

        for (int i = 0; i < msg.length; i++)
        {
            if (i % 2 == 0)
            {
                gamma_cipher.add(msg[i]);
            }
            else
            {
                delta_cipher.add(msg[i]);
            }
        }
    }

    public static int[] Proses_Get_Message(ArrayList<Integer> gamma_list, ArrayList<Integer> delta_list, int p, int x) {

        int[] gamma = new int[gamma_list.size()];
        int[] delta = new int[delta_list.size()];

        int index = 0;
        for (Integer i : gamma_list) {
            gamma[index] = i.intValue();
            index++;
        }

        int index2 = 0;
        for (Integer i : delta_list) {
            delta[index2] = i.intValue();
            index2++;
        }

        int[] val = new int[gamma.length];
        BigInteger BigInt_P = BigInteger.valueOf(p);
        for (int i = 0; i < gamma.length; i++)
        {
            BigInteger gammaBigInt = BigInteger.valueOf(gamma[i]);
            BigInteger deltaBigInt = BigInteger.valueOf(delta[i]);
            BigInteger gammaBigInt_pow_PX = gammaBigInt.pow(p - 1 - x);
            val[i] = deltaBigInt.multiply(gammaBigInt_pow_PX).remainder(BigInt_P).intValue();
        }

        return val;
    }

}
