package com.reddit.androidclient;


import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by kevin_000 on 8/9/2016.
 */
public class MyCache
{
    private static String cacheDirectory = "/Android/data/com.reddit.androidclient/cache/";
    static
    {
        if(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            cacheDirectory=Environment.getExternalStorageDirectory()
                    +cacheDirectory;
            File f=new File(cacheDirectory);
            f.mkdirs();
        }
    }

    public static String convertToCacheName(final String url)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte[] b=digest.digest();
            BigInteger bi=new BigInteger(b);
            return "mycache_"+bi.toString(16)+".cac";
        }
        catch (final Exception e)
        {
            Log.d("error", e.toString());
            return null;
        }
    }

    private static boolean tooOld(long time)
    {
        long now = new Date().getTime();
        long diff = now - time;
        return diff > 300000;
    }

    public static byte[] read(final String url)
    {
        try
        {
            String file = cacheDirectory+"/"+convertToCacheName(url);
            File f=new File(file);
            if (!f.exists() || f.length() == 0)
                return null;
            if(f.exists() && tooOld(f.lastModified()))
            {
                f.delete();
            }
            byte [] data = new byte[(int)f.length()];
            DataInputStream fis = new DataInputStream(new FileInputStream(f));
            fis.readFully(data);
            fis.close();
            return data;
        }
        catch (final Exception e)
        {
            return null;
        }
    }

    public static void write(String url, String data)
    {
        try
        {
            String file=cacheDirectory+"/"+convertToCacheName(url);
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(data);
            pw.close();
        }
        catch (Exception e)
        {
            System.out.println("Hopefully you don't get here!");
        }
    }
}
