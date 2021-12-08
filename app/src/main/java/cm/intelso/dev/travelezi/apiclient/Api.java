package cm.intelso.dev.travelezi.apiclient;

import java.util.List;

import cm.intelso.dev.travelezi.dto.AuthToken;
import cm.intelso.dev.travelezi.dto.Bus;
import cm.intelso.dev.travelezi.dto.GenericResponse;
import cm.intelso.dev.travelezi.dto.Line;
import cm.intelso.dev.travelezi.dto.POI;
import cm.intelso.dev.travelezi.dto.User;
import cm.intelso.dev.travelezi.dto.WaitingDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

//    String BASE_URL = "https://carprest.cm/api/";
    String BASE_URL = "https://192.168.100.45:8000/api/";

    @POST("register/")
    Call<GenericResponse> register(@Body User userDetails);

    @POST("login_check")
    Call<AuthToken> requestAuthToken(@Body User credentials);

    @GET("ext/poi/find")
    Call<List<POI>> findStops();

    @GET("ext/line/find")
    Call<List<Line>> findLines(
            @Query("uuid") String uuid,
            @Query("code") String code,
            @Query("number") String number,
            @Query("name") String name,
            @Query("type") String type,
            @Query("startPoint") String startPoint,
            @Query("endPoint") String endPoint
    );

    @GET("ext/bus/getlinebus")
    Call<List<Bus>> getLineBus(@Query("line") String line);

    @GET("ext/bus/getwaitingtime")
    Call<WaitingDetail> getWaitingTime(
            @Query("startLatitude") String startLat,
            @Query("startLongitude") String startLng,
            @Query("endLatitude") String endLat,
            @Query("endLongitude") String endLng
    );

//    @Headers("Content-Type: application/json")
    /*@PUT("sms/update")
    Call<Sms> updateSms(@Body Sms sms);*/
}
