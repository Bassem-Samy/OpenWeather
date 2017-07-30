package com.kbm.openweather.utils;

import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastItem;
import com.kbm.openweather.models.ForecastResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Bassem on 7/30/2017.
 */

public class ForecastToDaysConverter {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DISPLAY_HOUR_PATTERN = "hh:mm";

    public static List<ForecastDay> getForecastDaysFromResponse(ForecastResponse forecastResponse) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        SimpleDateFormat hourSimpleDateFormat = new SimpleDateFormat(DISPLAY_HOUR_PATTERN);
        List<ForecastDay> forecastDays = new ArrayList<>();
        HashMap<String, List<ForecastItem>> daysMap = new HashMap<>();
        List<ForecastItem> tempItems;
        for (ForecastItem item : forecastResponse.getForecastItems()
                ) {
            if (item.getDateText() != null) {
                String[] splits = item.getDateText().split(" ");
                if (splits.length == 2) {
                    tempItems = daysMap.get(splits[0]);
                    if (tempItems == null) {
                        tempItems = new ArrayList<>();
                    }
                    if (item.getDateTime() != null && item.getDateText() != "") {
                        item.setDisplayHour(hourSimpleDateFormat.format(new Date(Long.parseLong(item.getDateTime()) * 1000L)));
                    }

                    tempItems.add(item);
                    daysMap.put(splits[0], tempItems);
                }
            }
        }
        Iterator iterator = daysMap.entrySet().iterator();
        Map.Entry tempEntry;
        while (iterator.hasNext()) {
            tempEntry = (Map.Entry) iterator.next();
            ForecastDay forecastDay = new ForecastDay();
            forecastDay.setDateText((String) tempEntry.getKey());
            forecastDay.setForecastItems((List<ForecastItem>) tempEntry.getValue());
            try {
                forecastDay.setDate(simpleDateFormat.parse((String) tempEntry.getKey()));
            } catch (Exception ex) {
                forecastDay.setDate(new Date());
            }
            forecastDays.add(forecastDay);
            iterator.remove();
        }
        /**
         * TODO According to the requirement it should be 5 days only, but the forecast api returns the later 3 hours of the same day
         * logically it's ok to leave it display the later hours for today if want next 5 only then uncomment the following lines
         // if includes today remove it
         if (forecastDays.size() == 6) {
         forecastDays.remove(0);
         }*/
        Collections.sort(forecastDays);
        return forecastDays;

    }
}
