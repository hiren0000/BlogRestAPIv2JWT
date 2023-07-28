package com.rebel.BlogAPIv2.payloads;

import java.util.Random;

public class GenerateOtp
{
    public static Long otpGenerate()
    {
        Random random = new Random();
        Long Otp = random.nextLong(999999);


        return(Otp);
    }

    /**
    // Driver code
    public static void main(String[] args)
    {

        // Declare the length of OTP
        int len = 6;
        System.out.printf("Your OTP is - %s", generateOTP(len));
    }*/
}
