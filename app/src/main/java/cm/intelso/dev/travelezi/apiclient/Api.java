package cm.intelso.dev.travelezi.apiclient;

import java.util.List;

import cm.intelso.dev.travelezi.data.model.Bus;
import cm.intelso.dev.travelezi.data.model.Line;
import cm.intelso.dev.travelezi.data.model.POI;
import cm.intelso.dev.travelezi.data.model.Path;
import cm.intelso.dev.travelezi.data.model.Planning;
import cm.intelso.dev.travelezi.data.model.Start;
import cm.intelso.dev.travelezi.data.model.User;
import cm.intelso.dev.travelezi.dto.AuthToken;
import cm.intelso.dev.travelezi.dto.GenericResponse;
import cm.intelso.dev.travelezi.dto.StartIpt;
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

    @GET("ext/bus/getwaitingtimes")
    Call<List<WaitingDetail>> getWaitingTimes(
            @Query("startLatitude") String startLat,
            @Query("startLongitude") String startLng,
            @Query("endLatitude") String endLat,
            @Query("endLongitude") String endLng
    );

    @GET("ext/planning/find")
    Call<List<Planning>> findPlannings(
            @Query("uuid") String uuid,
            @Query("code") String code,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("startAt") String startAt,
            @Query("endAt") String endAt,
            @Query("status") String status,
            @Query("driverName") String driverName,
            @Query("lineCode") String lineCode,
            @Query("lineNumber") String lineNumber,
            @Query("lineName") String lineName,
            @Query("chassis") String busChassis,
            @Query("immatriculation") String busImmat,
            @Query("driver") String driverUuid,
            @Query("email") String email
    );

    @POST("ext/start/add")
    Call<GenericResponse> addStart(@Body StartIpt startDetails);

    @GET("ext/start/find")
    Call<List<Start>> findStarts(
            @Query("uuid") String uuid,
            @Query("startDeparture") String startDeparture,
            @Query("endDeparture") String endDeparture,
            @Query("startArrival") String startArrival,
            @Query("endArrival") String endArrival,
            @Query("status") String status,
            @Query("path") String path,
            @Query("planning") String planning,
            @Query("controller") String controller,
            @Query("controlStatus") String controlStatus
    );

    @GET("ext/path/find")
    Call<List<Path>> findPaths(
            @Query("uuid") String uuid,
            @Query("startPoint") String startPoint,
            @Query("endPoint") String endPoint
    );

//    @Headers("Content-Type: application/json")
    /*@PUT("sms/update")
    Call<Sms> updateSms(@Body Sms sms);*/
}
