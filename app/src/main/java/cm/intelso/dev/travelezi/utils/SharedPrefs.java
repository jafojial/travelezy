package cm.intelso.dev.travelezi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by ALEX on 02/02/2016.
 */
public class SharedPrefs {

    public static final String PREFS_KEY = "__PREFS__" ;
    public final String PREFS_APP_FIRST_RUN_KEY = "__FIRST_RUN__" ;
    public final String PREFS_PIN_CODE_CREATED_KEY = "__PIN_CODE_CREATED__" ;
    public final String PREFS_PIN_CODE_KEY = "__PIN_CODE__" ;
    public final String PREFS_APP_FIRST_SETTING_KEY = "__FIRST_SETTING__" ;

    public final String PREFS_SETTINGS_USERNAME_KEY = "__USERNAME__" ;
    public final String PREFS_SETTINGS_AT_HOME_KEY = "__AT_HOME__" ;

    public final String PREFS_SETTINGS_ACCOUNT_0NE_KEY = "__ACCOUNT_0NE__" ;
    public final String PREFS_SETTINGS_ACCOUNT_TWO_KEY = "__ACCOUNT_TWO__" ;
    public final String PREFS_SETTINGS_OPERATOR_ONE_NAME_KEY = "__OPERATOR_ONE_NAME__" ;
    public final String PREFS_SETTINGS_OPERATOR_TWO_NAME_KEY = "__OPERATOR_TWO_NAME__" ;
    public final String PREFS_SETTINGS_OPERATOR_ONE_NUMBER_KEY = "__OPERATOR_ONE_NUMBER__" ;
    public final String PREFS_SETTINGS_OPERATOR_TWO_NUMBER_KEY = "__OPERATOR_TWO_NUMBER__" ;
    public final String PREFS_SETTINGS_OM_ACCOUNT_SELECTED_KEY = "__OM_ACCOUNT_SELECTED__" ;
    public final String PREFS_SETTINGS_MMM_ACCOUNT_SELECTED_KEY = "__MMM_ACCOUNT_SELECTED__" ;
    /*
    public final String PREFS_SETTINGS_DATE_OF_BIRTH_KEY = "__DATE_OF_BIRTH__" ;
    public final String PREFS_SETTINGS_GOV_ID_KEY = "__GORVERNMENT_ID__" ;
    public final String PREFS_SETTINGS_ACCOUNT_ID_KEY = "__ACCOUNT_ID__" ;
    public final String PREFS_SETTINGS_MERCHANT_MODE_KEY = "__MERCHANT_MODE__" ;
    public final String PREFS_SETTINGS_NYM_ID_LOCATION_KEY = "__NYM_ID_LOCATION__" ;
    */

    public SharedPrefs() {
        super();
    }

    public void save(Context context, String key, Boolean bool) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(key, bool); //3

        editor.commit(); //4
    }

    public void save(Context context, String key, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(key, text); //3

        editor.commit(); //4
    }

    public Boolean getBooleanValue(Context context, String key) {
        SharedPreferences settings;
        Boolean bool;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        bool = settings.getBoolean(key, false);
        return bool;
    }

    public String getStringValue(Context context, String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        text = settings.getString(key, "");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context, String key) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.commit();
    }

/*
    public void toastPost(LayoutInflater lInflater, View v, Context ctx, int msg, int duration){
        final View layout = lInflater.inflate(R.layout.layout_toast, (ViewGroup) v.findViewById(R.id.toast_layout));
        TextView toastMsg = (TextView) layout.findViewById(R.id.toast_text);
        Toast toast = new Toast(ctx);
        toastMsg.setText(msg);
        toast.setView(layout);
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }
*/
}
