package cm.intelso.dev.travelezi.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 */
public class SharedPrefs {

    public static final String PREFS_KEY = "__PREFS__" ;
    public final String PREFS_APP_INSTALLED_KEY = "__INSTALLED__" ;
    public final String PREFS_APP_DEFAULT_URL_KEY = "__DEFAULT_URL__" ;
    public final String PREFS_APP_DEFAULT_PARAMETERS_KEY = "__DEFAULT_PARAMETERS__";
    public final String PREFS_APP_DEFAULT_AGENT_TYPE_KEY = "__DEFAULT_AGENT_TYPE__";
    public final String PREFS_APP_MOMO_PARAMETERS_KEY = "__MOMO_PARAMETERS__";
    public final String PREFS_APP_MODE_KEY = "__MODE__" ;
    public final String PREFS_USER_TOKEN_KEY = "__TOKEN__" ;
    public final String PREFS_USER_CURRENT_STOP_KEY = "__CURRENT_STOP__" ;
    public final String PREFS_USER_NEXT_STOP_KEY = "__NEXT_STOP__" ;
    public final String PREFS_AGENT_ID_KEY = "__AGENT_ID__" ;
    public final String PREFS_AGENT_LOGIN_KEY = "__AGENT_LOGIN__" ;
    public final String PREFS_AGENT_COMPLETE_NAME_KEY = "__AGENT_COMPLETE_NAME__" ;
    public final String PREFS_TYPE_VOYAGE_KEY = "__TYPE_VOYAGE__" ;
    public final String PREFS_ID_VILLE_DEPART_KEY = "__ID_VILLE_DEPART__" ;
    public final String PREFS_VILLE_DEPART_KEY = "__VILLE_DEPART__" ;
    public final String PREFS_ID_VILLE_DESTINATION_KEY = "__ID_VILLE_DESTINATION__" ;
    public final String PREFS_VILLE_DESTINATION_KEY = "__VILLE_DESTINATION__" ;
    public final String PREFS_ID_TRAJET_ALLER_KEY = "__ID_TRAJET_ALLER__" ;
    public final String PREFS_ID_TRAJET_RETOUR_KEY = "__ID_TRAJET_RETOUR__" ;
    public final String PREFS_ID_CLASSE_KEY = "__ID_CLASSE__" ;
    public final String PREFS_CLASSE_KEY = "__CLASSE__" ;
    public final String PREFS_DATE_DEPART_KEY = "__DATE_DEPART__" ;
    public final String PREFS_DATE_RETOUR_KEY = "__DATE_RETOUR__" ;
    //public final String PREFS_ID_DEPART_KEY = "__ID_DEPART__" ;
    public final String PREFS_HEURE_DEPART_KEY = "__HEURE_DEPART__" ;
    //public final String PREFS_ID_RETOUR_KEY = "__ID_RETOUR__" ;
    public final String PREFS_HEURE_RETOUR_KEY = "__HEURE_RETOUR__" ;
    public final String PREFS_ID_VOYAGE_ALLER_KEY = "__ID_VOYAGE_ALLER__" ;
    public final String PREFS_ID_VOYAGE_RETOUR_KEY = "__ID_VOYAGE_RETOUR__" ;
    public final String PREFS_PRIX_KEY = "__PRIX__" ;

    public final String PREFS_USER_ID_KEY = "__USER_ID__" ; // subscriptionID
    public final String PREFS_ACCOUNT_ID_KEY = "__ACCOUNT_ID__" ;
    public final String PREFS_USERNAME_KEY = "__USERNAME__" ; // identifiant
    public final String PREFS_USER_COMPLETE_NAME_KEY = "__USER_COMPLETE_NAME__" ;
    public final String PREFS_USER_CNI_NUMBER_KEY = "__USER_CNI_NUMBER__" ;
    public final String PREFS_USER_PHONE_NUMBER_KEY = "__USER_PHONE_NUMBER__" ;
    public final String PREFS_USER_EMAIL_KEY = "__USER_EMAIL__" ;
    public final String PREFS_LUGGAGES_KEY = "__LUGGAGES__" ;
    public final String PREFS_PAIEMENT_MODE_KEY = "__PAIEMENT_MODE__" ;

    public final String PREFS_CARD_SCAN_KEY = "__CARD_SCAN__" ;

    public final String PREFS_TICKET_KEY = "__TICKET__" ;
    public final String PREFS_STATUS_KEY = "__STATUS__" ;
    public final String PREFS_TICKET_CODE_KEY = "__TICKET_CODE__" ;
    public final String PREFS_ID_VENTE_KEY = "__ID_VENTE__" ;

    public final String PREFS_PRINT_TICKET_KEY = "__PRINT_TICKET__" ;

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

}
