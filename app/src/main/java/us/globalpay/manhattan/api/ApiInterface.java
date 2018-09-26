package us.globalpay.manhattan.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import us.globalpay.manhattan.models.api.AuthenticateReqBody;
import us.globalpay.manhattan.models.api.AuthenticateResponse;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.NicknameReqBody;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
import us.globalpay.manhattan.utils.StringsURL;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public interface ApiInterface
{
    @GET(StringsURL.COUNTRIES)
    Call<Countries> getCountries();

    @Headers("Content-Type: application/json")
    @POST(StringsURL.AUTHENTICATE_CONSUMER)
    Call<AuthenticateResponse> authenticateConsumer(@Body AuthenticateReqBody pAuthenticateBody,
                                                    @Header("AppVersion") String pAppVersion,
                                                    @Header("Platform") String pPlatform,
                                                    @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.VALIDATE_NICKNAME)
    Call<SimpleResultResponse> registerNickname(@Header("authenticationKey") String pAuthKey,
                                                @Body NicknameReqBody pNicknameRequest,
                                                @Header("AppVersion") String pAppVersion,
                                                @Header("Platform") String pPlatform,
                                                @Header("PackageName") String packageName);


}
