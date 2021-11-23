package cm.intelso.dev.travelezi.utils;

import android.app.Activity;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by JAFOJIAL on 01/07/2016.
 */
public class TravelDate {

    Activity currentActivity;
    TextView dateTextView;

    static final int DATEPICKER_DIALOG_ID = 0;
    //static final int TIMEPICKER_DIALOG_ID = 1;
    private DatePicker datePicker;
    private Calendar calendar;
    int year, month, day;

    public TravelDate(Activity activity, TextView textView){
        currentActivity=activity;
        dateTextView=textView;
    }

}

