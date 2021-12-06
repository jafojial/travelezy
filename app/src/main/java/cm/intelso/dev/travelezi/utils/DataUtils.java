package cm.intelso.dev.travelezi.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cm.intelso.dev.travelezi.R;
import cm.intelso.dev.travelezi.json.JsonController;

/**
 * Created by JAFOJIAL on 01/07/2016.
 */
public class DataUtils {

    public static String pubKey = "-----BEGIN PUBLIC KEY-----" +
            "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmDn5jJ3c8BBpvT8J4v00" +
            "xR/Izu0sxWV9aseLZ1sHnlzjLgHQ5nw83xlHK6DRaaAmWOPGYn+/OAtaiFwWo2Ns" +
            "dWC7W1vrWtvIVoX6UMclSd0PRzECagmmZM7fO3R5iPlZRpyAemPBls53I3fVe0eV" +
            "GXbSGEzcJHxljTBUE+tAZbi8vWk+HOSmZVqwsdH++jNzhywLFtDiSGRnq+EozNch" +
            "TIRsz1wxCMQGDf35ShyV87AtVuSd0vm2/lS8TOiTRMrG/9jqcmqPl4C8xrySohLa" +
            "TNPrVazEXJ1thzG/FOi4Zqowm/teaACUmRce6ihUyWv3ULSJVg0YSIfwq4Am4YHJ" +
            "ztLilRbfnftwFdlOX3zAraFih4jP8yA7uzU57HBCzkoe3rnvKeiGXhBASyLBqhh7" +
            "s2wn21ucoVutZDSijfP897YYNudZfJBCXeHhQwIaTUrEuiEz+nlOzFvDjb7DTwWp" +
            "7pkBVibSpNbfASHQm5mqNJ157uVED6tTz6uwVHyTq6hnJM5A8r3LFkFQVoHHO3RH" +
            "n37ICAZT8aV310My0MLTTanUNYOncwbrR0x1JfkBVfrtWQAuKoObIDTv8zJkcj3d" +
            "Ox+RpnDZ5TYN+Xi1hHc8L2c/t8hSsDF7TngNRgUxGGtrRsyHPrrXFAl1sKC3k/Ga" +
            "Gc/m9yMUbUIaaxb5RzZZEzcCAwEAAQ==" +
            "-----END PUBLIC KEY-----";

    // Progress dialog
    public ProgressDialog pDialog;
    private String jsonResponse;

    public HashMap<Integer,String> villesData = new HashMap<>();
    public String typeVoyage = "";
    public String classeVoyage = "";
    public String villeDepart = "";
    public String villeDestination = "";
    public String dateDepart = "";
    public String dateRetour = "";
    public String heureDepart = "";
    public String heureRetour = "";
    public String prix = "";
    public String nombreBagages = "";
    public String nomPassager = "";
    public String cniPassager = "";
    public String numeroTelephonePassager = "";
    public String emailPassager = "";

    public int getSelectedID(Spinner spinner){
        int id = 999999;
        for(Map.Entry<Integer, String> entry : villesData.entrySet()) {
            //int key = entry.getKey();
            //String value = entry.getValue();
            if(spinner.getSelectedItem().toString().equals(entry.getValue())){
                id = entry.getKey();
            }
        }
        return id;
    }

    public String getHeureOnly(String bus){
        return bus.substring(0,5);
    }

    public boolean isValideDate(String date){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int annee = Integer.parseInt(date.substring(0, 4));
        int mois = Integer.parseInt(date.substring(5, 7));
        int jour = Integer.parseInt(date.substring(8, date.length()));

        if(annee<year || (mois<month && annee<=year) || (jour<day && mois<=month && annee<=year)){
            return false;
        }

        return true;
    }

    public HashMap<Integer, String> getCityJsonArrayRequest(String url) {

        showpDialog();

        final ArrayList<String> villes = new ArrayList<String>();
        final ArrayList<Integer> idVilles = new ArrayList<Integer>();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            //ArrayList<String> villes = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ville = (JSONObject) response.get(i);

                                int id = ville.getInt("id");
                                String name = ville.getString("lib_designation");
                                idVilles.add(id);
                                villes.add(name);
                                villesData.put(id, name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        JsonController.getInstance().addToRequestQueue(req);
        return villesData;
    }

    private void showpDialog() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = pubKey;
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

    public static ProgressDialog createProgressDialog(Context ctx){
        ProgressDialog pDialog =  new ProgressDialog(ctx);
        pDialog.setMessage(ctx.getString(R.string.wait));
        pDialog.setCancelable(false);

        return pDialog;
    }

    public static ProgressDialog createProgressDialog(Context ctx, String msg, Boolean cancelable){
        ProgressDialog pDialog =  new ProgressDialog(ctx);
        pDialog.setMessage(msg);
        pDialog.setCancelable(cancelable);

        return pDialog;
    }

    public static void showProgressDialog(ProgressDialog pDialog) {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hideProgressDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting());
    }

}
