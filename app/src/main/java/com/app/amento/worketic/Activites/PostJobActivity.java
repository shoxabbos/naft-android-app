package uz.itmaker.naft.Activites;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import uz.itmaker.naft.Adapters.MyAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.TaxonomyListing.DurationList;
import uz.itmaker.naft.Model.TaxonomyListing.EnglishLevel;
import uz.itmaker.naft.Model.TaxonomyListing.FreelancerLevel;
import uz.itmaker.naft.Model.TaxonomyListing.Language;
import uz.itmaker.naft.Model.TaxonomyListing.Location;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectCat;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectType;
import uz.itmaker.naft.Model.TaxonomyListing.Projectlevel;
import uz.itmaker.naft.Model.TaxonomyListing.Skill;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.FileUtils;
import uz.itmaker.naft.view.InternetConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import cn.pedant.SweetAlert.SweetAlertDialog;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import okhttp3.RequestBody;

public class PostJobActivity extends BaseActivity {
    private static final String TAG = SendProposalActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    private EditText job_title , job_cost , job_content , job_address , job_latitude , job_longitude;
    private Button btnChoose;
    private Spinner project_level_spinner , job_duration , freelancer_type  , english_level_spinner , job_type;
    List<DurationList> arrayList_duration;
    List<Projectlevel> arrayList_level;
    List<FreelancerLevel> arrayList_freelancer_level;
    List<EnglishLevel> arrayList_english_level;
    List<ProjectCat> arrayList_category;
    List<ProjectType> arrayList_type;
    String[] categoryArray;
    String[] languagesArray;
    String[] skillsArray;
    MultiSelectSpinner category;
    MultiSelectSpinner language;
    MultiSelectSpinner skill;
    List<Language> arrayList_languages;
    List<Skill> arrayList_skill;
    List<Location> arrayList_location;
    Spinner location;
    String[] locationArray;
    private ArrayList<Uri> arrayList;
    private ListView listView;
    String user_id;
    String keyNameStr_project_level , keyNameStr_job_duration , keyNameStr_freelancer_type , keyNameStr_english_level
            , keyNameStr_job_type , keyNameStr_location ,keyNameStr_featured , keyNameStr_attachment ;
    TextView featured_value , attachment_value;
    RequestBody title;
    RequestBody project_level;
    RequestBody project_duration;
    RequestBody freelancer_level;
    RequestBody english_level;
    RequestBody project_cost;
    RequestBody description;
    RequestBody country;
    RequestBody address;
    RequestBody longitude;
    RequestBody latitude;
    RequestBody is_featured;
    RequestBody show_attachments;
    RequestBody size;
    String[] categories;
    String[] languages;
    String[] skills;
    private Button send_post;
    private Switch featured_switch;
    private Switch attachment_switch;
    ProgressBar dd_loading;
    private SweetAlertDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_post_job);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initData();
        setData();

    }

    private void initData(){
        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        arrayList = new ArrayList<>();
        job_title = findViewById(R.id.ui_description);
        job_cost = findViewById(R.id.ui_cost);
        project_level_spinner = findViewById(R.id.ui_project_level);
        job_duration = findViewById(R.id.ui_job_duration);
        freelancer_type = findViewById(R.id.ui_freelancer_type);
        english_level_spinner = findViewById(R.id.ui_english_level);
        job_type = findViewById(R.id.ui_job_type);
        category = findViewById(R.id.ui_category_list_post_job);
        language= findViewById(R.id.ui_language_list_post_job);
        skill= findViewById(R.id.ui_skill_list_post_job);
        location= findViewById(R.id.ui_location_list_post_job);
        btnChoose = findViewById(R.id.ui_select_file_post_job);
        listView = findViewById(R.id.list_view_post_job);
        job_content = findViewById(R.id.ui_short_description);
        job_address = findViewById(R.id.ui_your_address);
        job_latitude = findViewById(R.id.ui_latitude);
        job_longitude = findViewById(R.id.ui_longitude);
        attachment_value = findViewById(R.id.attachment_job_value);
        featured_value = findViewById(R.id.featured_job_value);
        featured_switch = findViewById(R.id.ui_featured_check);
        attachment_switch = findViewById(R.id.ui_attachment_check);
        send_post = findViewById(R.id.ui_send_post);
        dd_loading = findViewById(R.id.ui_bookloading);


//        if (user.getProfile().getPmeta().getFeaturJobOp().equals(true)){
        featured_switch.setVisibility(View.VISIBLE);
        featured_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    featured_value.setText("on");
                }else {
                    featured_value.setText("off");
                }
            }
        });

//        }else if (user.getProfile().getPmeta().getFeaturJobOp().equals(false)){
//            featured_switch.setVisibility(View.GONE);
//        }




        attachment_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    attachment_value.setText("on");
                }else {
                    attachment_value.setText("off");
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the file chooser dialog
                if (askForPermission())
                    showChooser();
            }
        });



        project_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_level != null && arrayList_level.size() != 0){
                    keyNameStr_project_level = arrayList_level.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });


        job_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_type != null && arrayList_type.size() != 0){
                    keyNameStr_job_type = arrayList_type.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        send_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostJob();
            }
        });

    }
    private void setData(){

        dd_loading.setVisibility(View.VISIBLE);
        Call<List<Projectlevel>> call_level = RetrofitClient.getInstance().getApi().getProjectLevel("project_level");
        call_level.enqueue(new Callback<List<Projectlevel>>() {
            @Override
            public void onResponse(Call<List<Projectlevel>> call, Response<List<Projectlevel>> response) {
//              dd_loading.setVisibility(View.GONE);
                arrayList_level =response.body();
                for (Projectlevel C:arrayList_level){
                    if (C.getTitle() != null){
                        Log.d("Level" , C.getTitle());
                        final String[] levelName = new String[arrayList_level.size()];
                        for (int i=0 ; i<arrayList_level.size() ; i++){
                            levelName[i]= arrayList_level.get(i).getTitle();
                        }
                        project_level_spinner.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                levelName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Projectlevel>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , "Format Error" , Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Location>> call_location = RetrofitClient.getInstance().getApi().getlocationlist("locations");
        call_location.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
//              dd_loading.setVisibility(View.GONE);
                arrayList_location =response.body();
                for (Location C:arrayList_location){
                    if (C.getTitle() != null){
                        Log.d("Location" , C.getTitle() );
                        final String[] languageName = new String[arrayList_location.size()];
                        for (int i=0 ; i<arrayList_location.size() ; i++){
                            languageName[i]= arrayList_location.get(i).getTitle();
                        }
                        location.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<DurationList>> call_rates = RetrofitClient.getInstance().getApi().getduration("duration_list");
        call_rates.enqueue(new Callback<List<DurationList>>() {
            @Override
            public void onResponse(Call<List<DurationList>> call, Response<List<DurationList>> response) {
//                dd_loading.setVisibility(View.GONE);
                arrayList_duration =response.body();
                for (DurationList C:arrayList_duration){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList_duration.size()];
                        for (int i=0 ; i<arrayList_duration.size() ; i++){
                            languageName[i]= arrayList_duration.get(i).getTitle();
                        }
                        job_duration.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<DurationList>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<FreelancerLevel>> freelancerType = RetrofitClient.getInstance().getApi().getFreelancerLevel("freelancer_level");
        freelancerType.enqueue(new Callback<List<FreelancerLevel>>() {
            @Override
            public void onResponse(Call<List<FreelancerLevel>> call, Response<List<FreelancerLevel>> response) {
//              dd_loading.setVisibility(View.GONE);
                arrayList_freelancer_level =response.body();
                for (FreelancerLevel C:arrayList_freelancer_level){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList_freelancer_level.size()];
                        for (int i=0 ; i<arrayList_freelancer_level.size() ; i++){
                            languageName[i]= arrayList_freelancer_level.get(i).getTitle();
                        }
                        freelancer_type.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<FreelancerLevel>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });



        Call<List<EnglishLevel>> englishLevel = RetrofitClient.getInstance().getApi().getenglishlist("english_levels");
        englishLevel.enqueue(new Callback<List<EnglishLevel>>() {
            @Override
            public void onResponse(Call<List<EnglishLevel>> call, Response<List<EnglishLevel>> response) {
//                dd_loading.setVisibility(View.GONE);
                arrayList_english_level =response.body();
                for (EnglishLevel C:arrayList_english_level){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList_english_level.size()];
                        for (int i=0 ; i<arrayList_english_level.size() ; i++){
                            languageName[i]= arrayList_english_level.get(i).getTitle();
                        }
                        english_level_spinner.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<EnglishLevel>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });



        Call<List<ProjectCat>> call_category = RetrofitClient.getInstance().getApi().getProjectCategory("project_cat");
        call_category.enqueue(new Callback<List<ProjectCat>>() {
            @Override
            public void onResponse(Call<List<ProjectCat>> call, Response<List<ProjectCat>> response) {
                arrayList_category =response.body();
                for (ProjectCat C:arrayList_category){
                    if (C.getName() != null){
                        Log.d("Category" , C.getName() );
                        final String[] languageName = new String[arrayList_category.size()];
                        for (int i=0 ; i<arrayList_category.size() ; i++){
                            languageName[i]= Html.fromHtml(arrayList_category.get(i).getName()).toString();
                        }

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(PostJobActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        category.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {


                                categoryArray = new String[arrayList_category.size()];

                                for(int j = 0; j < arrayList_category.size(); j++){
                                    if(selected[j]) {
                                        categoryArray[j] = arrayList_category.get(j).getId().toString();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<ProjectCat>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });



        Call<List<Language>> call_languages = RetrofitClient.getInstance().getApi().getlanguages("languages");
        call_languages.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                arrayList_languages =response.body();
                for (Language C:arrayList_languages){
                    if (C.getName() != null){
                        Log.d("Languages" , C.getName() );
                        final String[] languageName = new String[arrayList_languages.size()];
                        for (int i=0 ; i<arrayList_languages.size() ; i++){
                            languageName[i]= arrayList_languages.get(i).getName();
                        }

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(PostJobActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        language.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {

                                languagesArray = new String[arrayList_languages.size()];

                                for(int j = 0; j < arrayList_languages.size(); j++){
                                    if(selected[j]) {
                                        languagesArray[j] = arrayList_languages.get(j).getId().toString();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Skill>> call_skills = RetrofitClient.getInstance().getApi().getskill("skills");
        call_skills.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                dd_loading.setVisibility(View.GONE);
                arrayList_skill =response.body();
                for (Skill C:arrayList_skill){
                    if (C.getName() != null){
                        Log.d("Skills" , C.getName());
                        final String[] languageName = new String[arrayList_skill.size()];
                        for (int i=0 ; i<arrayList_skill.size() ; i++){
                            languageName[i]= arrayList_skill.get(i).getName();
                        }
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(PostJobActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        skill.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {
                                skillsArray = new String[arrayList_skill.size()];

                                for(int j = 0; j < arrayList_skill.size(); j++){
                                    if(selected[j]) {
                                        skillsArray[j] = arrayList_skill.get(j).getId().toString();
                                    }
                                }
                            }
                        }).setSelectAll(false).setMinSelectedItems(0);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(PostJobActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


        job_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_duration != null && arrayList_duration.size() != 0){
                    keyNameStr_job_duration = arrayList_duration.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        freelancer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_freelancer_level != null && arrayList_freelancer_level.size() != 0){
                    keyNameStr_freelancer_type = arrayList_freelancer_level.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        english_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_english_level != null && arrayList_english_level.size() != 0){
                    keyNameStr_english_level = arrayList_english_level.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_location != null && arrayList_location.size() != 0){
                    keyNameStr_location = arrayList_location.get(position).getId().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

    }

    private void PostJob(){

        if (InternetConnection.checkConnection(PostJobActivity.this)){

            RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
            showProgress();
            List<MultipartBody.Part> attachments = new ArrayList<>();
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    attachments.add(prepareFilePart("attachments" + i, arrayList.get(i)));
                }
            }
            user_id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
            title = createPartFromString(job_title.getText().toString().trim());
            project_level = createPartFromString(keyNameStr_project_level);
            project_duration = createPartFromString(keyNameStr_job_duration);
            freelancer_level = createPartFromString(keyNameStr_freelancer_type);
            english_level = createPartFromString(keyNameStr_english_level);
            project_cost = createPartFromString(job_cost.getText().toString().trim());
            description = createPartFromString(job_content.getText().toString().trim());
            country = createPartFromString(keyNameStr_location);
            address = createPartFromString(job_address.getText().toString().trim());
            longitude = createPartFromString(job_longitude.getText().toString().trim());
            latitude = createPartFromString(job_latitude.getText().toString().trim());
            is_featured = createPartFromString(featured_value.getText().toString().trim());
            show_attachments = createPartFromString(attachment_value.getText().toString().trim());
            categories = categoryArray;
            skills = skillsArray;
            languages = languagesArray;
            size = createPartFromString(""+attachments.size());

            Call<ResponseBody> call_upload_job = RetrofitClient.getInstance().getApi().Uploadjob(user_id, title, project_level, project_duration
                    , freelancer_level, english_level,  project_cost, description, country, address, longitude, latitude
                    , is_featured, show_attachments, categories, skills, languages ,size ,  attachments);
            call_upload_job.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        hideProgress();
                        pDialog = new SweetAlertDialog(PostJobActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Good job!");
                        pDialog.setContentText("Job posted successfully!");
                        pDialog.show();
                    } else {
                        hideProgress();
                        pDialog = new SweetAlertDialog(PostJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Ой...");
                        pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                        pDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideProgress();
                    pDialog = new SweetAlertDialog(PostJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Please check your network!");
                    pDialog.show();
                }
            });

        }else {
            hideProgress();
            Toast.makeText(PostJobActivity.this,
                    R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if(data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while(currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                String path = FileUtils.getPath(this, imageUri);
                                Log.d("Multiple File Selected", path);

                                arrayList.add(imageUri);
                                MyAdapter mAdapter = new MyAdapter(PostJobActivity.this, arrayList);
                                listView.setAdapter(mAdapter);

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if(data.getData() != null) {
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);

                            arrayList.add(uri);
                            MyAdapter mAdapter = new MyAdapter(PostJobActivity.this, arrayList);
                            listView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri){

        File file = FileUtils.getFile(this, fileUri);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void showChooser(){
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
        }
    }

    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(PostJobActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(PostJobActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(PostJobActivity.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                } else {
                    ActivityCompat.requestPermissions(PostJobActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener){

        AlertDialog.Builder builder = new AlertDialog.Builder(PostJobActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(PostJobActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(PostJobActivity.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showChooser();
                } else {
                    Toast.makeText(PostJobActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
    private void showProgress() {
        dd_loading.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        dd_loading.setVisibility(View.GONE);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
