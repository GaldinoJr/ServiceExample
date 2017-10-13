package com.example.galdino.serviceexample;

import java.util.Random;

/**
 * Created by Galdino on 12/10/2017.
 */

public class GenerateRandomNumber
{
    public static int get()
    {
        int min = 65;
        int max = 80;

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        return randomNumber;
    }
}
