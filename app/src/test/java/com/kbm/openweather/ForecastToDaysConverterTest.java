package com.kbm.openweather;

import com.google.gson.Gson;
import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastItem;
import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.utils.ForecastToDaysConverter;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Bassem on 7/30/2017.
 * A unit test that checks ForecastToDaysConverter functions correctly where it converts forecast items and group them by day and sort them in ascending order.
 */

public class ForecastToDaysConverterTest {
    private static final int EXPECTED_ITEMS_COUNT = 5;
    private static final String FIRST_ITEM_EXPECTED_DATE_TEXT = "2017-07-31";
    private static final int FIRST_ITEM_EXPECTED_FORECAST_ITEMS_SIZE = 8;
    private static final int LAST_ITEM_ITEM_INDEX = 4;
    private static final String LAST_ITEM_EXPECTED_DATE_TEXT = "2017-08-04";
    private static final int LAST_ITEM_EXPECTED_FORECAST_ITEMS_SIZE = 8;
    private ForecastResponse response;

    @Before
    public void setup() throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("forecast_response.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        Gson gson = new Gson();
        response = gson.fromJson(builder.toString(), ForecastResponse.class);
    }

    @Test
    public void testConverterPersonList() {
        List<ForecastDay> items = ForecastToDaysConverter.getForecastDaysFromResponse(response);
        // check items size are correct
        assertEquals(items.size(), EXPECTED_ITEMS_COUNT);
        // check first item date and hours count is correct
        assertEquals(items.get(0).getDateText(), FIRST_ITEM_EXPECTED_DATE_TEXT);
        assertEquals(items.get(0).getForecastItems().size(), FIRST_ITEM_EXPECTED_FORECAST_ITEMS_SIZE);
        // check last item date and hours count is correct
        assertEquals(items.get(LAST_ITEM_ITEM_INDEX).getDateText(), LAST_ITEM_EXPECTED_DATE_TEXT);
        assertEquals(items.get(LAST_ITEM_ITEM_INDEX).getForecastItems().size(), LAST_ITEM_EXPECTED_FORECAST_ITEMS_SIZE);
    }
}
