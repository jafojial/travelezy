package cm.intelso.dev.travelezi.apiclient;

import java.util.List;

import cm.intelso.dev.travelezi.dto.AuthToken;
import cm.intelso.dev.travelezi.dto.GenericResponse;
import cm.intelso.dev.travelezi.dto.POI;
import cm.intelso.dev.travelezi.dto.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

//    String BASE_URL = "https://carprest.cm/api/";
    String BASE_URL = "https://192.168.100.45:8000/api/";

    @POST("register/")
    Call<GenericResponse> register(@Body User userDetails);

    @POST("login_check")
    Call<AuthToken> requestAuthToken(@Body User credentials);

    @GET("ext/poi/find")
    Call<List<POI>> findStops();

//    @Headers("Content-Type: application/json")
    /*@PUT("sms/update")
    Call<Sms> updateSms(@Body Sms sms);*/
}
