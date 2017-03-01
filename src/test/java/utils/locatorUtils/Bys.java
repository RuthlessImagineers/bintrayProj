package utils.locatorUtils;

import org.openqa.selenium.By;

/**
 * Created by krishnanand on 01/08/16.
 */
public class Bys {

    public static final By PROGRESS_SPINNER = By.id("progress");

    public static final By LOADER = By.id("main_screen_list_animation_iv");

    public static final By CURRENT_EXPIRY_MONTH = By.xpath("//android.widget.NumberPicker[1]//android.widget.EditText");

    public static final By CURRENT_EXPIRY_YEAR = By.xpath("//android.widget.NumberPicker[2]//android.widget.EditText");

    public static final By EXPIRY_MONTH_BTN = By.xpath("//android.widget.NumberPicker[1]//android.widget.Button");

    public static final By EXPIRY_MONTH_BTN_BOTTOM = By.xpath("//android.widget.NumberPicker[1]//android.widget.Button[2]");

    public static final By EXPIRY_YEAR_BTN = By.xpath("//android.widget.NumberPicker[2]//android.widget.Button");

    public static final By EXPIRY_YEAR_BTN_BOTTOM = By.xpath("//android.widget.NumberPicker[2]//android.widget.Button[2]");

    public static final By BOOKING_REF_TEXT = By.id("trip_confirmation_booking_ref");

    public static final By MISSING_VALUE = By.id("check_title_tv");

}
