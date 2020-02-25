package uz.itmaker.naft.Interface;

import uz.itmaker.naft.Model.Category;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.EmployerOfferProjects.Project;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.Model.TaxonomyListing.DurationList;
import uz.itmaker.naft.Model.TaxonomyListing.EnglishLevel;
import uz.itmaker.naft.Model.TaxonomyListing.FreelancerLevel;
import uz.itmaker.naft.Model.TaxonomyListing.Language;
import uz.itmaker.naft.Model.TaxonomyListing.Location;
import uz.itmaker.naft.Model.TaxonomyListing.NoOfEmploye;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectCat;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectType;
import uz.itmaker.naft.Model.TaxonomyListing.Projectlevel;
import uz.itmaker.naft.Model.TaxonomyListing.Rate;
import uz.itmaker.naft.Model.TaxonomyListing.Reason;
import uz.itmaker.naft.Model.TaxonomyListing.Skill;
import uz.itmaker.naft.Model.profileData;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    String BASE_SITE = "http://naft.uz/";

    String BASE_URL = BASE_SITE + "api/v1/";

    @GET("list/get-categories")
    Call<List<Category>> getTopCategories();

    @FormUrlEncoded
    @POST("user/do-login")
    Call<RetrofitClientLogin> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("listing/get-freelancers")
    Call<List<ProviderModel>> getHomeFreelancer(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users
    );

    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> getLatestjob(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users
    );

    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> getjobbycategories(
            @Query("listing_type") String listing_type,
            @Query("category") String categroy

    );
    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> getCompleteLatestjob(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("page_number") int page_number

    );

    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> getCompanyJobs(
            @Query("listing_type") String listing_type,
            @Query("company_id") String company_id


    );

    @GET("listing/get-freelancers")
    Call<List<ProviderModel>> getCompleteFreelancer(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("page_number") int page_number
    );


    @GET("listing/get-employers")
    Call<List<Employer>> getCompanyListing(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("page_number") int page_number

    );

    @GET("profile/setting")
    Call<profileData> getUserProfile(@Query("id") String id );

    @POST("user/do-logout")
    Call<ResponseBody> logout(
            @Query("user_id") String user_id
    );

    @GET("taxonomies/get-list")
    Call<List<Rate>> getList(
            @Query("list") String list
    );

    @GET("taxonomies/get-list")
    Call<List<EnglishLevel>> getenglishlist(
            @Query("list") String list
    );

    @GET("taxonomies/get-list")
    Call<List<FreelancerLevel>> getFreelancerLevel(
            @Query("list") String list

    );

    @GET("taxonomies/get-list")
    Call<List<DurationList>> getduration(
            @Query("list") String list

    );

    @GET("taxonomies/get-list")
    Call<List<ProjectType>> getProjecttype(
            @Query("list") String list

    );

    @GET("taxonomies/get-taxonomy")
    Call<List<Language>> getlanguages(
            @Query("taxonomy") String taxonomy

    );

    @GET("taxonomies/get-taxonomy")
    Call<List<Skill>> getskill(
            @Query("taxonomy") String taxonomy

    );

    @GET("taxonomies/get-taxonomy")
    Call<List<Location>> getlocationlist(
            @Query("taxonomy") String taxonomy

    );

    @GET("taxonomies/get-taxonomy")
    Call<List<ProjectCat>> getProjectCategory(
            @Query("taxonomy") String taxonomy

    );

    @GET("taxonomies/get-taxonomy")
    Call<List<NoOfEmploye>> getnoOfemployee(
            @Query("taxonomy") String taxonomy

    );

    @FormUrlEncoded
    @POST("user/update-profile")
    Call<ResponseBody> UpdateProfile(
            @Query("user_id") String user_id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("user_type") String user_type,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("hourly_rate") String hourly_rate,
            @Field("location_id") int location_id,
            @Field("employees") int employees,
            @Field("counry") String country,
            @Field("adress") String address,
            @Field("tagline") String tagline,
            @Field("gender") String gender
    );


    @GET("listing/get-freelancers")
    Call<List<ProviderModel>> SearchFreelancer(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("keyword") String keyword,
            @Query("skills[]") String[] skills,
            @Query("location[]") String[] location,
//          @Query("hourly_rate") String hourly_rate,
            @Query("type[]") String[] type,
            @Query("english_level[]") String[] english_level,
            @Query("language[]") String[] language
    );

    @GET("listing/get-freelancers")
    Call<List<ProviderModel>> getFav_Freelancer(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type
    );

    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> getFav_job(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type
    );

    @GET("listing/get-employers")
    Call<List<Employer>> getFav_company(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type
    );

    @GET("listing/get-jobs")
    Call<List<LatestJobModel>> SearchJob(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("keyword") String keyword,
            @Query("categories[]") String[] categories,
            @Query("skills[]") String[] skills,
            @Query("location[]") String[] location,
            @Query("type[]") String[] type,
            @Query("duration[]") String[] duration,
            @Query("project_type[]") String[] project_type	,
            @Query("language[]") String[] language

    );

    @GET("listing/get-employers")
    Call<List<Employer>> SearchComapny(
            @Query("profile_id") String profile_id,
            @Query("listing_type") String listing_type,
            @Query("show_users") int show_users,
            @Query("keyword") String keyword,
            @Query("location[]") String[] location,
            @Query("employees[]") String[] employees

    );

    @POST("user/update-favourite")
    Call<ResponseBody> UpdateFavourite(
            @Query("id") String id,
            @Query("favorite_id") int favorite_id,
            @Query("type") String type
    );

    @Multipart
    @POST("user/submit-proposal")
    Call<ResponseBody> uploadMultiple(
            @Query("user_id") String user_id,
            @Query("project_id") String project_id,
            @Part("proposed_amount") RequestBody proposed_amount,
            @Part("proposed_time") RequestBody proposed_time,
            @Part("proposed_content") RequestBody proposed_content,
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> attachments);


    @Multipart
    @POST("media/upload-media")
    Call<ResponseBody> uploadProfile(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part profile_image
    );

    @Multipart
    @POST("media/upload-media")
    Call<ResponseBody> uploadbanner(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part banner_image
    );

    @GET("taxonomies/get-list")
    Call<List<Reason>> getReason(
            @Query("list") String list
    );


    @FormUrlEncoded
    @POST("user/reporting")
    Call<ResponseBody> ReportUser(
            @Query("user_id") String user_id,
            @Query("id") int id,
            @Field("reason") String reason,
            @Field("description") String description
    );
    @FormUrlEncoded
    @POST("user/send-offer")
    Call<ResponseBody> Send_Employer_offer(

            @Query("user_id") String user_id,
            @Query("freelancer_id") int freelancer_id,
            @Query("job_id") int job_id,
            @Field("desc") String desc
    );

    @GET("employer-jobs")
    Call<List<Project>> getoffer_project(
            @Query("employer_id") String employer_id
    );

    @FormUrlEncoded
    @POST("password/reset")
    Call<ResponseBody> recoverPassword(@Field("email") String email);

    @Multipart
    @POST("listing/add-jobs")
    Call<ResponseBody> Uploadjob(
            @Query("user_id") String user_id,
            @Part("title") RequestBody title,
            @Part("project_level") RequestBody project_level,
            @Part("project_duration") RequestBody project_duration,
            @Part("freelancer_level") RequestBody freelancer_level,
            @Part("english_level") RequestBody english_level,
            @Part("project_cost") RequestBody project_cost,
            @Part("description") RequestBody description,
            @Part("country") RequestBody country,
            @Part("address") RequestBody address,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude,
            @Part("is_featured") RequestBody is_featured,
            @Part("show_attachments") RequestBody show_attachments,
            @Query("categories[]") String[] categories,
            @Query("skills[]") String[] skills,
            @Query("languages[]") String[] languages,
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> attachments
    );

    @GET("taxonomies/get-list")
    Call<List<Projectlevel>> getProjectLevel(
            @Query("list") String list

    );

}
