package com.shelby.android.healthviz;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by John on 12/6/2014.
 */
public final class CcdDecode {

    public static String decodeAge(Date birthDate, Context appContext) {
        Calendar now = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.setTime(birthDate);

        int yn = now.get(Calendar.YEAR);
        int yb = b.get(Calendar.YEAR);

        int mn = now.get(Calendar.MONTH);
        int mb = b.get(Calendar.MONTH);

        int dn = now.get(Calendar.DAY_OF_MONTH);
        int db = b.get(Calendar.DAY_OF_MONTH);

        int y = yn - yb;
        int m = mn - mb;
        int d = dn - db;

        if (d < 0) {
            switch(mn) {
                case 4:
                case 6:
                case 9:
                case 11:
                    d = d + 30;
                    break;
                case 2:
                    if (yn % 4 == 0) {
                        d = d + 29;
                    }
                    else {
                        d = d + 28;
                    }
                    break;
                default:
                    d = d + 31;
                    break;
            }

            m = m - 1;
        }

        if (m < 0) {
            m = m + 12;
            y = y - 1;
        }

        return appContext.getResources().getQuantityString(R.plurals.years, y, y) + " " + appContext.getResources().getQuantityString(R.plurals.months, m, m) + " " + appContext.getResources().getQuantityString(R.plurals.days, d, d);
    }

    public static String decodeUse(String use) {
        if (use.toUpperCase().equals("W")) {
            return "Work";
        }
        else if (use.toUpperCase().equals("WP")) {
            return "Work primary";
        }
        else if (use.toUpperCase().equals("H")) {
            return "Home";
        }
        else if (use.toUpperCase().equals("HP")) {
            return "Home primary";
        }

        return use;
    }

    public static String decodeMaritalStatus(String code) {
        if (code.toUpperCase().equals("M")) {
            return "Married";
        }
        else if (code.toUpperCase().equals("A")) {
            return "Annulled";
        }
        else if (code.toUpperCase().equals("D")) {
            return "Divorced";
        }
        else if (code.toUpperCase().equals("I")) {
            return "Interlocutory";
        }
        else if (code.toUpperCase().equals("L")) {
            return "Legally Separated";
        }
        else if (code.toUpperCase().equals("P")) {
            return "Polygamous";
        }
        else if (code.toUpperCase().equals("S")) {
            return "Never Married";
        }
        else if (code.toUpperCase().equals("T")) {
            return "Domestic Partner";
        }
        else if (code.toUpperCase().equals("W")) {
            return "Widowed";
        }
        else {
            return code;
        }
    }

    public static String decodeReligiousAffiliation(String code) {
        // ref https://phinvads.cdc.gov/vads/ViewValueSet.action?id=6BFDBFB5-A277-DE11-9B52-0015173D1785
        if (code.equals("1013")) {
            return "Christian";
        }
        else if (code.equals("1023")) {
            return "Islam";
        }
        else if (code.equals("1026")) {
            return "Judaism";
        }
        else if (code.toUpperCase().equals("1020")) {
            return "Hinduism";
        }
        else {
            return code;
        }
    }

    public static String decodeRaceEthnicity(String code) {
        // ref https://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.6.238
        if (code.equals("2058-6")) {
            return "African American";
        }
        else if (code.equals("2056-0")) {
            return "Black";
        }
        else if (code.equals("2106-3")) {
            return "White";
        }
        else if (code.equals("2135-2")) {
            return "Hispanic or Latino";
        }
        else if (code.equals("2054-5")) {
            return "Black or African American";
        }
        else if (code.equals("2186-5")) {
            return "Not Hispanic or Latino";
        }
        else if (code.equals("2034-7")) {
            return "Chinese";
        }
        else if (code.equals("2039-6")) {
            return "Japanese";
        }
        else {
            return code;
        }
    }
}
