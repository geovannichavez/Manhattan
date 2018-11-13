package us.globalpay.manhattan.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import us.globalpay.manhattan.models.api.BrandCouponsReq;
import us.globalpay.manhattan.models.api.CouponPurchaseReq;
import us.globalpay.manhattan.models.api.CouponsRequest;
import us.globalpay.manhattan.models.api.CouponsResponse;
import us.globalpay.manhattan.models.api.AuthenticateReqBody;
import us.globalpay.manhattan.models.api.AuthenticateResponse;
import us.globalpay.manhattan.models.api.BrandsReqBody;
import us.globalpay.manhattan.models.api.Countries;
import us.globalpay.manhattan.models.api.FavoriteCouponReq;
import us.globalpay.manhattan.models.api.GetCouponReq;
import us.globalpay.manhattan.models.api.GetCouponResponse;
import us.globalpay.manhattan.models.api.NicknameReqBody;
import us.globalpay.manhattan.models.api.OpenGiftReq;
import us.globalpay.manhattan.models.api.PromosRequest;
import us.globalpay.manhattan.models.api.RegisterClientResponse;
import us.globalpay.manhattan.models.api.RegisterPhoneConsumerReqBody;
import us.globalpay.manhattan.models.api.SimpleResultResponse;
import us.globalpay.manhattan.models.api.SmsValidationReqBody;
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

    @Headers("Content-Type: application/json")

    @POST(StringsURL.GET_BRANDS)
    Call<JsonObject> getBrands(@Body BrandsReqBody requestBody,
                               @Header("authenticationKey") String userAuthenticationKey,
                               @Header("AppVersion") String versionName,
                               @Header("Platform") String platform,
                               @Header("PackageName") String packageName);

    @POST(StringsURL.REGISTER_PHONE_CONSUMER)
    Call<RegisterClientResponse> registerConsumer(@Header("authenticationKey") String pAuthKey,
                                                  @Header("AppVersion") String pAppVersion,
                                                  @Header("Platform") String pPlatform,
                                                  @Header("PackageName") String packageName,
                                                  @Body RegisterPhoneConsumerReqBody registerConsumerBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.VALIDATE_TOKEN_LOCAL_AUTH)
    Call<JsonObject> requestSmsValidation(@Body SmsValidationReqBody pTokenValBody,
                                          @Header("authenticationKey") String pAuthKey,
                                          @Header("AppVersion") String pAppVersion,
                                          @Header("Platform") String pPlatform,
                                          @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.GET_INITIAL_DATA)
    Call<JsonObject> getInitialData(@Header("authenticationKey") String userAuthenticationKey,
                                   @Header("AppVersion") String versionName,
                                   @Header("Platform") String platform,
                                   @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.RECLAIM_COUPON)
    Call<GetCouponResponse> getCouponByCoins(@Header("authenticationKey") String userAuthenticationKey,
                                             @Header("AppVersion") String versionName,
                                             @Header("Platform") String platform,
                                             @Header("PackageName") String packageName);

                                             
    @Headers("Content-Type: application/json")
    @POST(StringsURL.GET_PROMOS)
    Call<JsonObject> getPromos(@Body PromosRequest request,
                                            @Header("authenticationKey") String userAuthenticationKey,
                                             @Header("AppVersion") String versionName,
                                             @Header("Platform") String platform,
                                             @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.GET_COUPONS)
    Call<JsonObject> getCoupons(@Body CouponsRequest couponsRequest,
                                     @Header("authenticationKey") String userAuthenticationKey,
                                     @Header("AppVersion") String versionName,
                                     @Header("Platform") String platform,
                                     @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.GET_COUPONS_BY_BRAND)
    Call<JsonObject> getBrandCoupons(@Body BrandCouponsReq couponsRequest,
                                @Header("authenticationKey") String userAuthenticationKey,
                                @Header("AppVersion") String versionName,
                                @Header("Platform") String platform,
                                @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.COUPON_PURCHASE)
    Call<JsonObject> purchaseCoupon(@Body CouponPurchaseReq purchaseReq,
                                    @Header("authenticationKey") String userAuthenticationKey,
                                    @Header("AppVersion") String versionName,
                                    @Header("Platform") String platform,
                                    @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.GET_COUPON)
    Call<JsonObject> getBrandCoupon(@Body GetCouponReq request,
                                             @Header("authenticationKey") String userAuthenticationKey,
                                             @Header("AppVersion") String versionName,
                                             @Header("Platform") String platform,
                                             @Header("PackageName") String packageName);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.FAVORITE_COUPON)
    Call<JsonObject> saveCoupon(@Body FavoriteCouponReq request,
                                    @Header("authenticationKey") String userAuthenticationKey,
                                    @Header("AppVersion") String versionName,
                                    @Header("Platform") String platform,
                                    @Header("PackageName") String packageName);


    @Headers("Content-Type: application/json")
    @POST(StringsURL.OPEN_GIFT_OBJECT)
    Call<JsonObject> openGift(@Body OpenGiftReq request,
                              @Header("authenticationKey") String userAuthenticationKey,
                              @Header("AppVersion") String versionName,
                              @Header("Platform") String platform,
                              @Header("PackageName") String packageName);

    }
