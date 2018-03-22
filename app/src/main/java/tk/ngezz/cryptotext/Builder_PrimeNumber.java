package tk.ngezz.cryptotext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erikodiony on 3/15/2018.
 */

public class Builder_PrimeNumber {

    //VARIABLE
    public static int[] listPublicKey;
    public static int[] listPrivateKey;

    //FUNCTION
    public static int[] generatePublicKey() {
        int[] number = new int[1000 - 256];
        int idx = 0;
        for (int i = 256; i < 1000; i++)
        {
            number[idx] = i;
            idx++;
        }

        List<Integer> listPrime = new ArrayList<>();
        for (int i =0; i < number.length; i++)
        {
            if (isPrime(number[i]) == true)
            {
                listPrime.add(number[i]);
            }
        }

        int[] prime = new int[listPrime.size()];
        int index = 0;
        for (Integer x : listPrime)
        {
            prime[index] = x.intValue();
            index++;
        }

        return prime;
    }

    public static int[] generatePrivateKey() {
        int[] number = new int[250 - 2];
        int idx = 0;
        for (int i = 2; i < 250; i++)
        {
            number[idx] = i;
            idx++;
        }
        return number;
    }

    public static int CreateRandomKey(String param) {
        if (param == "PrivateKey")
        {
            listPrivateKey = generatePrivateKey();
            int rnd = new Random().nextInt(listPrivateKey.length);
            return listPrivateKey[rnd];
        }
        else
        {
            listPublicKey = generatePublicKey();
            int rnd = new Random().nextInt(listPublicKey.length);
            return listPublicKey[rnd];
        }
    }

    public static boolean Check_PrivateKey(int num, int pubKey) {
        if (1 < num)
        {
            if (num <= (pubKey - 2))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean Check_PublicKey(int num) {
        return isPrime(num);
    }

    public static boolean isPrime(int num) {
        if (num > 2 && num % 2 == 0)
        {
            return false;
        }
        else
        {
            int top = (int)Math.sqrt(num) + 1;
            for (int i = 3; i < top; i += 2)
            {
                if (num % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }

}
