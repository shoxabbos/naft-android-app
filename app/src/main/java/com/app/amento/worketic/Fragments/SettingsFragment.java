package uz.itmaker.naft.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import uz.itmaker.naft.Activites.PostJobActivity;
import uz.itmaker.naft.Activites.SearchActivity;
import uz.itmaker.naft.Interface.Api;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.Company.Employer;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.TaxonomyListing.Location;
import uz.itmaker.naft.Model.TaxonomyListing.NoOfEmploye;
import uz.itmaker.naft.Model.TaxonomyListing.Rate;
import uz.itmaker.naft.Model.UploadImage;
import uz.itmaker.naft.Model.profileData;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener , OnMapReadyCallback {

    private static final int SELECT_PROFILE_PIC = 1;
    private static final int SELECT_BANNER_PIC = 2;
    private View mMainView;
    String mediaPath;
    private SupportMapFragment mapFragment;
    private CircleImageView wt_profile_view;
    private ImageView wt_banner_view;
    private GoogleMap gMap;
    private TextView wt_country , ui_gender , ui_department , ui_employee , company_text , ui_country;
    private List<UploadImage> profile_base64;
    private EditText wt_name  , wt_lastname , wt_tagline , wt_address , wt_latitude , wt_longitude , wt_rate ;
    private profileData userData;
    RequestBody id;
    private ProgressDialog mRegProgress;
    private SweetAlertDialog pDialog;
    private CircleImageView upload_profile_image ;
    private Button upload_profile_banner , update_profile_activity ;
    private ImageView dd_profile_view , dd_banner_view;
    private LinearLayout gender , rate , detail , select_gender , select_country , selected_country , employee_layout  , main_employee_layout;
    private View companyView;
    private String user_id;
    private RadioGroup radiogroup;
    private RadioButton male_btn , female_btn;
    private Spinner location_spinner , employee_spinner;
    List<Location> arrayList;
    List<NoOfEmploye> arrayList_employee;
    private int keyNameStr;
    private int keyNameStr_employee;

    public SettingsFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView =  inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        wt_profile_view =  mMainView.findViewById(R.id.profile_icon);
        wt_banner_view =  mMainView.findViewById(R.id.banner_icon);
        mRegProgress = new ProgressDialog(getActivity());
        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        Picasso.get().load(user.getProfile().getPmeta().getProfileImg()).into(wt_profile_view);
        Picasso.get().load(user.getProfile().getPmeta().getBannerImg()).into(wt_banner_view);

        initViews();
        setListener();
        setData();

        update_profile_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        return mMainView;
    }
    private void initViews(){

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();

        wt_address= mMainView.findViewById(R.id.ui_profile_address);
        wt_name= mMainView.findViewById(R.id.ui_profile_name);
        wt_lastname= mMainView.findViewById(R.id.ui_profile_last_name);
        wt_latitude= mMainView.findViewById(R.id.ui_profile_latitude);
        wt_longitude= mMainView.findViewById(R.id.ui_profile_longitude);
        wt_tagline= mMainView.findViewById(R.id.ui_profile_tagline);
        wt_country= mMainView.findViewById(R.id.ui_profile_country);
        wt_rate= mMainView.findViewById(R.id.ui_profile_rate);
        upload_profile_image= mMainView.findViewById(R.id.add_profile_image);
        upload_profile_banner= mMainView.findViewById(R.id.ui_add_banner);
        dd_profile_view=mMainView.findViewById(R.id.profile_icon);
        dd_banner_view=mMainView.findViewById(R.id.banner_icon);
        ui_gender = mMainView.findViewById(R.id.ui_profile_gender);
        ui_department = mMainView.findViewById(R.id.ui_profile_department);
        ui_employee = mMainView.findViewById(R.id.ui_profile_no_of_employee);
        gender = mMainView.findViewById(R.id.layout_gender);
        rate = mMainView.findViewById(R.id.layout_rate);
        company_text = mMainView.findViewById(R.id.ui_company_detail_text);
        companyView = mMainView.findViewById(R.id.ui_company_detail_line);
        detail = mMainView.findViewById(R.id.ui_company_detail);
        update_profile_activity = mMainView.findViewById(R.id.ui_save_settings);
        radiogroup = (RadioGroup) mMainView.findViewById(R.id.ui_radiogroup_signup);
        male_btn = (RadioButton) mMainView.findViewById(R.id.ui_male_radiobtn);
        female_btn = (RadioButton) mMainView.findViewById(R.id.ui_female_radiobtn);
        select_gender = mMainView.findViewById(R.id.layout_select_gender);
        location_spinner = mMainView.findViewById(R.id.ui_spinner_location);
        selected_country = mMainView.findViewById(R.id.layout_select_country);
        select_country = mMainView.findViewById(R.id.country_layout);
        employee_spinner = mMainView.findViewById(R.id.ui_spinner_employees);
        employee_layout = mMainView.findViewById(R.id.layout_select_no_of_employees);
        main_employee_layout = mMainView.findViewById(R.id.ui_main_employee_layout);

        Call<List<Location>> call_rates = RetrofitClient.getInstance().getApi().getlocationlist("locations");
        call_rates.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                arrayList =response.body();
                for (Location C:arrayList){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList.size()];
                        for (int i=0 ; i<arrayList.size() ; i++){
                            languageName[i]= arrayList.get(i).getTitle();
                        }
                        location_spinner.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(getActivity() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });




        Call<List<NoOfEmploye>> call_employee = RetrofitClient.getInstance().getApi().getnoOfemployee("no_of_employes");
        call_employee.enqueue(new Callback<List<NoOfEmploye>>() {
            @Override
            public void onResponse(Call<List<NoOfEmploye>> call, Response<List<NoOfEmploye>> response) {
                arrayList_employee =response.body();
                for (NoOfEmploye C:arrayList_employee){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList_employee.size()];
                        for (int i=0 ; i<arrayList_employee.size() ; i++){
                            languageName[i]= arrayList_employee.get(i).getTitle();
                        }
                        employee_spinner.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<NoOfEmploye>> call, Throwable t) {
                Toast.makeText(getActivity() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList != null && arrayList.size() != 0){
                    keyNameStr = arrayList.get(position).getId();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        employee_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_employee != null && arrayList_employee.size() != 0){
                    keyNameStr_employee = arrayList_employee.get(position).getValue();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });



        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radiogroup.getCheckedRadioButtonId();
                switch (option)
                {
                    case R.id.ui_male_radiobtn:
                        if (male_btn.isChecked()){
                            ui_gender.setText("male");
                        }
                    case R.id.ui_female_radiobtn:
                        if (female_btn.isChecked()){
                            ui_gender.setText("female");
                        }
                }
            }
        });

        if (user.getProfile().getPmeta().getUserType().equals("employer")){
            gender.setVisibility(View.GONE);
            select_gender.setVisibility(View.GONE);
            rate.setVisibility(View.GONE);


            select_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_country.setVisibility(selected_country.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });

            main_employee_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    employee_layout.setVisibility(employee_layout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });

        }
        if (user.getProfile().getPmeta().getUserType().equals("freelancer")) {
            detail.setVisibility(View.GONE);
            companyView.setVisibility(View.GONE);
            company_text.setVisibility(View.GONE);


            gender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_gender.setVisibility(select_gender.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });

            select_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_country.setVisibility(selected_country.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });
        }
    }

    private void setData(){

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        if (user != null && SharedPreferenceUtil.getBoolen(getActivity(), Constants.ISUSERLOGGEDIN)) {
            String id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
            final Call<profileData> profileData_responce = RetrofitClient.getInstance().getApi().getUserProfile(id);
            profileData_responce.enqueue(new Callback<profileData>() {
                @Override
                public void onResponse(Call<profileData> call, Response<profileData> response) {
                    if (response.code() ==200){
                        onProfileLoaded(response.body());
                    }
                }

                @Override
                public void onFailure(Call<profileData> call, Throwable t) {

                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onProfileLoaded(profileData items){

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();

            userData = items;
            if (items != null){
                mapFragment.getMapAsync(this);
                wt_country.setText(items.getLocation());
                wt_latitude.setText(items.getLatitude());
                wt_longitude.setText(items.getLongitude());
                wt_name.setText(items.getFirstName());
                wt_lastname.setText(items.getLastName());
                wt_address.setText(items.getAddress());
                wt_rate.setText(items.getPerHourRate());
                wt_tagline.setText(items.getTagLine());
                ui_gender.setText(items.getGender());
                ui_department.setText(items.getDepartment());
                ui_employee.setText(items.getNoOfEmployees());

            }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (map != null){
            LatLng newLatLong = new LatLng(47.5162,
                    14.5501);
            map.addMarker(new MarkerOptions().position(newLatLong));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLong, 15f));
        }
    }

    private void uploadProfileImage(){
        mRegProgress.setTitle("Updating profile Image");
        mRegProgress.setMessage("Пожалуйста подождите");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        File pathName = new File(mediaPath);
        RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"), pathName);
        MultipartBody.Part profile_image = MultipartBody.Part.createFormData("profile_image", pathName.getName(), requestBody);
        id = RequestBody.create(MediaType.parse("text/plain") , DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId()) ;

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadProfile(id , profile_image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200){
                    mRegProgress.hide();
                    String s  = response.body().toString();
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Good job!");
                    pDialog.setContentText("Profile image successfully!");
                    pDialog.show();
                }else if (response.code() == 203){
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                    pDialog.show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void uploadBannerImage(){
        mRegProgress.setTitle("Updating banner Image");
        mRegProgress.setMessage("Пожалуйста подождите");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        File pathName = new File(mediaPath);
        RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"), pathName);
        MultipartBody.Part banner_image = MultipartBody.Part.createFormData("banner_image", pathName.getName(), requestBody);
        id = RequestBody.create(MediaType.parse("text/plain") , DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId()) ;

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadbanner(id , banner_image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200){
                    mRegProgress.hide();
                    String s  = response.body().toString();
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Good job!");
                    pDialog.setContentText("Banner image updated successfully!");
                    pDialog.show();
                }else if (response.code() == 203){
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                    pDialog.show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setListener(){
        upload_profile_image.setOnClickListener(this);
        upload_profile_banner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.add_profile_image:
                PickImage(SELECT_PROFILE_PIC);
                break;
            case R.id.ui_add_banner:
                PickImage(SELECT_BANNER_PIC);
                break;
        }
    }

    private void PickImage(int type) {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);

            } else {
                getImageFromGallery(type);
            }
        } else {
            getImageFromGallery(type);
        }
    }

    private void getImageFromGallery(int type) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        String[] extraMimeTypes = {"image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
        startActivityForResult(intent, type);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PROFILE_PIC:
                PickImage(SELECT_PROFILE_PIC);
                break;
            case SELECT_BANNER_PIC:
                PickImage(SELECT_BANNER_PIC);
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PROFILE_PIC:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    dd_profile_view.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();


                    uploadProfileImage();
                }
                break;
            case SELECT_BANNER_PIC:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    dd_banner_view.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();

                    uploadBannerImage();
                }
                break;
        }
    }


    public void updateProfile(){

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        user_id = user.getProfile().getUmeta().getId();
        String first_name =  wt_name.getText().toString().trim();
        String last_name = wt_lastname.getText().toString().trim();
        String user_type = user.getProfile().getPmeta().getUserType().trim();
        String longitude = wt_longitude.getText().toString().trim();
        String latitude = wt_latitude.getText().toString().trim();
        String hourly_rate = wt_rate.getText().toString().trim();
        int    location_id = keyNameStr;
        int    employees = keyNameStr_employee;
        String country = wt_country.getText().toString().trim();
        String address = wt_address.getText().toString().trim();
        String tagline = wt_tagline.getText().toString().trim();
        String gender =  ui_gender.getText().toString().trim();

        mRegProgress.setTitle("Updating Profile Data");
        mRegProgress.setMessage("Пожалуйста подождите");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        Call<ResponseBody> Contact_Call = RetrofitClient.getInstance().getApi(). UpdateProfile( user_id , first_name , last_name ,
                user_type , longitude , latitude , hourly_rate , location_id  , employees , country , address , tagline , gender );
        Contact_Call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() ==200){
                    mRegProgress.dismiss();
                    String s  = response.body().toString();
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Good job!");
                    pDialog.setContentText("Profile Updated successfully!");
                    pDialog.show();

                }else if(response.code() == 203){
                    mRegProgress.dismiss();
                    pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                    pDialog.show();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mRegProgress.dismiss();
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("Ой...");
                pDialog.setContentText("Something went wrong!");
                pDialog.show();
            }
        });

    }
}
