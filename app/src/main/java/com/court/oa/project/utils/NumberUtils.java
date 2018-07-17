package com.court.oa.project.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by pengzhu on 2016/12/22.
 */

public class NumberUtils
{
    private static final DecimalFormat df = new DecimalFormat("#0.0");
    private static final DecimalFormat df1 = new DecimalFormat("##0.00");

    public static NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
    public static NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用

    /**
     * 保留一位小数显示
     *
     * @param value
     * @return
     */
    public static String getDFormat1(double value)
    {
        return df.format(value);
    }

    /**
     * 保留两位小数显示
     *
     * @param value
     * @return
     */
    public static String getDFormat2(double value)
    {
        return df1.format(value);
    }


    /**
     * 建立货币格式化引用
     * @param value
     * @return 例：￥15,000.48
     */
    public static String getCurrency(double value)
    {
        return currency.format(value);
    }

    /**
     *  建立百分比格式化引用
     * @param value
     * @return 例：0.8%
     */
    public static String getPercent(double value)
    {
        percent.setMaximumFractionDigits(3);
        return percent.format(value);
    }



}
