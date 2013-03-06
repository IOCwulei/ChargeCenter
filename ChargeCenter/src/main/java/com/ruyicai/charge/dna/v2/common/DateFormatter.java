package com.ruyicai.charge.dna.v2.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**�����ڸ�ʽת��������
 * 
 * @author XieminQuan
 */
public class DateFormatter {

    public static String HHmmss(Date date) {
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static String yyMM(Date date) {
        return new SimpleDateFormat("yyMM").format(date);
    }

    public static String MMdd(Date date) {
        return new SimpleDateFormat("MMdd").format(date);
    }

    public static String yyyy_MM_dd(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String yyMMdd(Date date) {
        return new SimpleDateFormat("yyMMdd").format(date);
    }

    public static String yy(Date year) {
        return new SimpleDateFormat("yy").format(year);
    }

    public static String yyyy_MM_dd_HH_mm_ss(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static String yyyyMMdd(Date time) {
        return new SimpleDateFormat("yyyyMMdd").format(time);
    }

    public static String china_yyyy_MM_dd_HH_mm_ss(Date time) {
        return  new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(time);
    }

    public static String HH_mm_ss_SSS(Date time) {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(time);
    }

    public static String yyyyMMddHHmmss(Date time) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(time);
    }

}
