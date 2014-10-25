package com.apps4med.healthious.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by iskitsas on 6/29/14.
 */
public class AppHelper {
    public static boolean isDemo = false;

    public static void alert(Context ctx, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.create();
        dialog.show();
    }




    public static double getTargetWeightInKilos(int remainingWeeks, double startWeight, double height) {
        // All in kilograms
        double MAXIMUM_WEIGHT_LOSS_PER_WEEK = 0.9;
        double MINIMUM_WEIGHT = 20.865;
        double MINIMUM_BMI = 18;
        double targetWeight = startWeight - (remainingWeeks * MAXIMUM_WEIGHT_LOSS_PER_WEEK);
        targetWeight = targetWeight >= MINIMUM_WEIGHT ? targetWeight : MINIMUM_WEIGHT;
        if (getBmi(targetWeight, height) < MINIMUM_BMI) {
            double d = MINIMUM_BMI * Math.pow(height / 100, 2);
            return Double.parseDouble(String.format("%.1f", d));
        }
        return targetWeight;
    }

    public static double getBmi(double weight, double height) {
        return weight / Math.pow(height / 100, 2);
    }

    public static Integer[] convertToEnglish(double kilos) {
        double pounds = kilos * 2.20462;
        String[] parts = String.valueOf(pounds).split("\\.");
        if (parts.length > 1) {
            String fractionPart = String.valueOf(parts[1]);
            if (fractionPart.startsWith("9")) {
                pounds  = Math.ceil(pounds);
            }
        }
        int stones = (int) (pounds / 14);
        int rem_pounds = (int) (Math.round(pounds % 14));
        if (stones == 0 && rem_pounds == 0) {
            rem_pounds = (int) Math.ceil(pounds);
        }
        return new Integer[]{stones, rem_pounds};
    }

    public static Double getKilosFromEnglish(int stones, int pounds) {
        return (stones * 14 + pounds) * 0.453592;
    }

    public static Integer[] convertHeightToEnglish(double centimeters) {
        double inches = centimeters / 2.54;
        int feet = (int) (inches / 12);
        int rem_inches = (int) (Math.round(inches % 12));

        if (feet == 0 && rem_inches == 0) {
            rem_inches = (int) Math.ceil(inches);
        }
        return new Integer[]{feet, rem_inches};
    }

    public static String getWeightClassification(double bmi) {
        String ret = "";
        if (bmi >= 30) {
            ret = Tag.obese.name();
        } else if (bmi >= 25) { //10.8
            ret = Tag.overweight.name();
        } else if (bmi < 18.50) {
            ret = Tag.underweight.name(); //23.4
        } else {
            ret = Tag.healthy.name(); //7.6
        }
        return ret;
    }



    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date);
    }

    public static String getCurrentDate(String p) {
        Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat(p);
        return format.format(date);
    }

    public static String getYesterday(String p) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        DateFormat format = new SimpleDateFormat(p);
        return format.format(date);
    }


    public static boolean isYesterday(String date) {
        Boolean ret = false;
        String now = AppHelper.getYesterday("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ret = formatter.parse(date).compareTo(formatter.parse(now)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean isToday(String date) {
        Boolean ret = false;
        String now = AppHelper.getCurrentDate("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ret = formatter.parse(date).compareTo(formatter.parse(now)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }



    public static boolean isThursday() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.DAY_OF_WEEK) == 5;//is thursday
    }



    public static String getGrams(String kgs) {
        return Double.parseDouble(kgs) * 1000 + "";
    }

    public static String getKgs(String grams) {
        Integer ret = null;
        Double gramsDouble = Double.parseDouble(grams);
        //check if kgs have been stored first
        boolean isKgs = gramsDouble > 0 && gramsDouble < 1000;

        if (!isKgs) {
            ret = ((Double) (gramsDouble / 1000)).intValue();
        } else {
            //old versions of app were sending kgs in the API calls. In order to support that, we identify if kgs have been stored first
            ret = gramsDouble.intValue();
        }
        return ret + "";
    }


}
