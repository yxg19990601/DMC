package com.ems.szy.util;

import java.util.Calendar;
import java.util.Date;

public class ByteArrayConveter {
    // char转换为byte[2]数组
    public static byte[] getByteArray(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xff00) >> 8);
        b[1] = (byte) (c & 0x00ff);
        return b;
    }

    // 从byte数组的index处的连续两个字节获得一个char
    public static char getChar(byte[] arr, int index) {
        return (char) (0xff00 & arr[index] << 8 | (0xff & arr[index + 1]));
    }
    // short转换为byte[2]数组
    public static byte[] getByteArray(short s) {
        byte[] b = new byte[2];
        b[0] = (byte) ((s & 0xff00) >> 8);
        b[1] = (byte) (s & 0x00ff);
        return b;
    }
    // 从byte数组的index处的连续两个字节获得一个short
    public static short getShort(byte[] arr, int index) {
        return (short) (0xff00 & arr[index] << 8 | (0xff & arr[index + 1]));
    }
    // int转换为byte[4]数组
    public static byte[] getByteArray(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) ((i & 0xff000000) >> 24);
        b[1] = (byte) ((i & 0x00ff0000) >> 16);
        b[2] = (byte) ((i & 0x0000ff00) >> 8);
        b[3] = (byte)  (i & 0x000000ff);
        return b;
    }
    // 从byte数组的index处的连续4个字节获得一个int
    public static int getInt(byte[] arr, int index) {
        return 	(0xff000000 	& (arr[index+0] << 24))  |
                (0x00ff0000 	& (arr[index+1] << 16))  |
                (0x0000ff00 	& (arr[index+2] << 8))   |
                (0x000000ff 	&  arr[index+3]);
    }

    // 从byte数组的index处的连续4个字节获得一个float
    public static float getFloat(byte[] arr, int index, boolean isReverse) {
        if (isReverse)
            arr = reserve(arr);

        return Float.intBitsToFloat(getInt(arr, index));
    }
    // long转换为byte[8]数组
    public static byte[] getByteArray(long l) {
        byte b[] = new byte[8];
        b[0] = (byte)  (0xff & (l >> 56));
        b[1] = (byte)  (0xff & (l >> 48));
        b[2] = (byte)  (0xff & (l >> 40));
        b[3] = (byte)  (0xff & (l >> 32));
        b[4] = (byte)  (0xff & (l >> 24));
        b[5] = (byte)  (0xff & (l >> 16));
        b[6] = (byte)  (0xff & (l >> 8));
        b[7] = (byte)  (0xff & l);
        return b;
    }
    // 从byte数组的index处的连续8个字节获得一个long
    public static long getLong(byte[] arr, int index) {
        return 	(0xff00000000000000L 	& ((long)arr[index+0] << 56))  |
                (0x00ff000000000000L 	& ((long)arr[index+1] << 48))  |
                (0x0000ff0000000000L 	& ((long)arr[index+2] << 40))  |
                (0x000000ff00000000L 	& ((long)arr[index+3] << 32))  |
                (0x00000000ff000000L 	& ((long)arr[index+4] << 24))  |
                (0x0000000000ff0000L 	& ((long)arr[index+5] << 16))  |
                (0x000000000000ff00L 	& ((long)arr[index+6] << 8))   |
                (0x00000000000000ffL 	&  (long)arr[index+7]);
    }
    // double转换为byte[8]数组
    public static byte[] getByteArray(double d) {
        long longbits = Double.doubleToLongBits(d);
        return getByteArray(longbits);
    }
    // 从byte数组的index处的连续8个字节获得一个double
    public static double getDouble(byte[] arr, int index) {
        return Double.longBitsToDouble(getLong(arr, index));
    }

    // 数组反转
    public static byte[] reserve( byte[] arr ){
        byte[] arr1 = new byte[arr.length];
        for( byte x=0;x<arr.length;x++ ){
            arr1[x] = arr[arr.length-x-1];
        }
        return arr1 ;
    }

    // float转换为byte[4]数组
    public static byte[] getByteArray(float f) {
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数
        return getByteArray(intbits);
    }




    public static String byteToHexStr(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            str.append(String.format("%02x",bytes[i]));
        }

        return str.toString();
    }




    // CRC 校验
    /******************************************************************************
     * Name:    CRC-16/XMODEM       x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0x0000
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000
     * Alias:   CRC-16/ZMODEM,CRC-16/ACORN
     *****************************************************************************/
    public static short crc16_xmodem(byte[] data, int offset,int length){
        byte i;
        short crc = 0;            // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= (short)data[j] << 8;
            for (i = 0; i < 8; ++i){
                if ( (crc & 0x8000) != 0)
                    crc = (short) ((crc << 1) ^ 0x1021);
                else
                    crc <<= 1;
            }
        }
        return crc;
    }


    // 时间转byte数组
    public static byte[] dateToByte(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR)-2000;
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new byte[]{(byte) (year&0xFF),(byte) (month&0xFF),(byte) (day&0xFF),(byte) (hour&0xFF),(byte) (minute&0xff)};
    }
    /**
     年- 月- 日-时-分-秒
     */
    public static Date byteToDate(byte[] bytes) {
        if (bytes == null || bytes.length != 5) {
            return null;
        }

        int year = 2000+(bytes[0]&0xFF);
        int month = (bytes[1] & 0xFF) -1;
        int day = bytes[2] & 0xFF;
        int hour = bytes[3] & 0xFF;
        int minute = bytes[4] & 0xFF;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }


    // 时间转byte数组
    public static byte[] dateToByteM(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR)-2000;
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new byte[]{(byte) (year&0xFF),(byte) (month&0xFF),(byte) (day&0xFF),(byte) (hour&0xFF),(byte) (minute&0xff),(byte)(second&0xff)};
    }


    /**
     年- 月- 日-时-分-秒
     */
    public static Date byteToDateM(byte[] bytes) {
        if (bytes == null || bytes.length != 6) {
            return null;
        }

        int year = 2000+(bytes[0]&0xFF);
        int month = (bytes[1] & 0xFF) -1;
        int day = bytes[2] & 0xFF;
        int hour = bytes[3] & 0xFF;
        int minute = bytes[4] & 0xFF;
        int second = bytes[5] & 0xFF;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return calendar.getTime();
    }
}
