package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.TaxonomyListing.DurationList;
import uz.itmaker.naft.Model.TaxonomyListing.EnglishLevel;
import uz.itmaker.naft.Model.TaxonomyListing.FreelancerLevel;
import uz.itmaker.naft.Model.TaxonomyListing.Language;
import uz.itmaker.naft.Model.TaxonomyListing.Location;
import uz.itmaker.naft.Model.TaxonomyListing.NoOfEmploye;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectCat;
import uz.itmaker.naft.Model.TaxonomyListing.ProjectType;
import uz.itmaker.naft.Model.TaxonomyListing.Rate;
import uz.itmaker.naft.Model.TaxonomyListing.Skill;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import java.util.List;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout keyword_linear_layout , category_linear_layout , skill_linear_layout , location_linear_layout ,
            hourly_rate_linear_layout , freelancer_type_linear_layout , project_level_linear_layout , project_type_linear_layout
            , english_level_linear_layout , no_of_emplyee_linear_layout , language_linear_layout ;
    private EditText keyword;
    private RadioGroup radiogroup;
    private RadioButton freelancer_btn , job_btn , company_btn;
    public Spinner hourly_rate_list;
    private Button ui_freelancer , ui_jobs , ui_company;
    List<Rate> arrayList;
    List<EnglishLevel> arrayList_english;
    List<Language> arrayList_languages;
    List<ProjectCat> arrayList_category;
    List<Skill> arrayList_skill;
    List<ProjectType> arrayList_type;
    List<Location> arrayList_location;
    List<FreelancerLevel> arrayList_freelancer;
    List<DurationList> arrayList_duration;
    List<NoOfEmploye> arrayList_employees;
    MultiSelectSpinner english;
    MultiSelectSpinner language;
    MultiSelectSpinner skill;
    MultiSelectSpinner location;
    MultiSelectSpinner freelancer;
    MultiSelectSpinner employee;
    MultiSelectSpinner category;
    MultiSelectSpinner type;
    MultiSelectSpinner duration;
    TextView Sample;
    String keyNameStr;
    String[] durationArray;
    String[] categoryArray;
    String[] englishLevelArray;
    String[] languagesArray;
    String[] skillsArray;
    String[] projectTypeArray;
    String[] locationArray;
    String[] freelancerLevelArray;
    String[] NoofEmployeeArray;
    ProgressBar dd_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_search_layout);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Sample = findViewById(R.id.sample_view);

        initData();
        setDatatoSpinner();
        setlistner();
    }

    private void initData(){

        dd_loading = findViewById(R.id.ui_bookloading);
        keyword = findViewById(R.id.ui_search_keyword);
        keyword_linear_layout = findViewById(R.id.ui_search_layout_keyword);
        category_linear_layout = findViewById(R.id.ui_search_layout_category);
        skill_linear_layout = findViewById(R.id.ui_search_layout_skill);
        location_linear_layout = findViewById(R.id.ui_search_layout_location);
        hourly_rate_linear_layout = findViewById(R.id.ui_search_layout_hourlyRate);
        freelancer_type_linear_layout = findViewById(R.id.ui_search_layout_freelancerType);
        project_level_linear_layout = findViewById(R.id.ui_search_layout_projectLength);
        project_type_linear_layout = findViewById(R.id.ui_search_layout_projectType);
        english_level_linear_layout = findViewById(R.id.ui_search_layout_englishLevel);
        no_of_emplyee_linear_layout = findViewById(R.id.ui_search_layout_numberEmplyee);
        language_linear_layout = findViewById(R.id.ui_search_layout_language);
        radiogroup =  findViewById(R.id.ui_radiogroup_signup);
        freelancer_btn =  findViewById(R.id.ui_freelancer_radiobtn);
        job_btn =  findViewById(R.id.ui_job_radiobtn);
        company_btn = findViewById(R.id.ui_employer_radiobtn);
        hourly_rate_list= findViewById(R.id.ui_hourly_rate_list);
        ui_freelancer = findViewById(R.id.ui_search_freelancer);
        ui_jobs = findViewById(R.id.ui_search_jobs);
        ui_company = findViewById(R.id.ui_search_company);
        english= findViewById(R.id.ui_english_level_list);
        language= findViewById(R.id.ui_language_list);
        skill= findViewById(R.id.ui_skill_list);
        location= findViewById(R.id.ui_location_list);
        freelancer= findViewById(R.id.ui_freelancer_level_list);
        employee= findViewById(R.id.ui_no_of_employee_list);
        category = findViewById(R.id.ui_category_list);
        type = findViewById(R.id.ui_project_type_list);
        duration = findViewById(R.id.ui_project_length_list);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radiogroup.getCheckedRadioButtonId();
                switch (option)
                {
                    case R.id.ui_freelancer_radiobtn:
                        if (freelancer_btn.isChecked()){
                            keyword_linear_layout.setVisibility(View.VISIBLE);
                            skill_linear_layout.setVisibility(View.VISIBLE);
                            location_linear_layout.setVisibility(View.VISIBLE);
                            hourly_rate_linear_layout.setVisibility(View.VISIBLE);
                            freelancer_type_linear_layout.setVisibility(View.VISIBLE);
                            project_level_linear_layout.setVisibility(View.GONE);
                            project_type_linear_layout.setVisibility(View.GONE);
                            english_level_linear_layout.setVisibility(View.VISIBLE);
                            no_of_emplyee_linear_layout.setVisibility(View.GONE);
                            language_linear_layout.setVisibility(View.VISIBLE);
                            category_linear_layout.setVisibility(View.GONE);
                            ui_company.setVisibility(View.GONE);
                            ui_jobs.setVisibility(View.GONE);
                            ui_freelancer.setVisibility(View.VISIBLE);
                        }
                    case R.id.ui_job_radiobtn:
                        if (job_btn.isChecked()){

                            keyword_linear_layout.setVisibility(View.VISIBLE);
                            skill_linear_layout.setVisibility(View.VISIBLE);
                            location_linear_layout.setVisibility(View.VISIBLE);
                            hourly_rate_linear_layout.setVisibility(View.GONE);
                            freelancer_type_linear_layout.setVisibility(View.VISIBLE);
                            project_level_linear_layout.setVisibility(View.VISIBLE);
                            project_type_linear_layout.setVisibility(View.VISIBLE);
                            english_level_linear_layout.setVisibility(View.GONE);
                            no_of_emplyee_linear_layout.setVisibility(View.GONE);
                            language_linear_layout.setVisibility(View.VISIBLE);
                            category_linear_layout.setVisibility(View.VISIBLE);
                            ui_freelancer.setVisibility(View.GONE);
                            ui_company.setVisibility(View.GONE);
                            ui_jobs.setVisibility(View.VISIBLE);

                        }
                    case R.id.ui_employer_radiobtn:
                        if (company_btn.isChecked()){

                            keyword_linear_layout.setVisibility(View.VISIBLE);
                            skill_linear_layout.setVisibility(View.GONE);
                            location_linear_layout.setVisibility(View.VISIBLE);
                            hourly_rate_linear_layout.setVisibility(View.GONE);
                            freelancer_type_linear_layout.setVisibility(View.GONE);
                            project_level_linear_layout.setVisibility(View.GONE);
                            project_type_linear_layout.setVisibility(View.GONE);
                            english_level_linear_layout.setVisibility(View.GONE);
                            no_of_emplyee_linear_layout.setVisibility(View.VISIBLE);
                            language_linear_layout.setVisibility(View.GONE);
                            category_linear_layout.setVisibility(View.GONE);
                            ui_freelancer.setVisibility(View.GONE);
                            ui_jobs.setVisibility(View.GONE);
                            ui_company.setVisibility(View.VISIBLE);

                        }
                }
            }
        });

        hourly_rate_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList != null && arrayList.size() != 0){
                    keyNameStr = arrayList.get(position).getValue();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
    }
    private void setDatatoSpinner(){

        //Multi selection spinner  for duration

        dd_loading.setVisibility(View.VISIBLE);
        Call<List<DurationList>> call_duration = RetrofitClient.getInstance().getApi().getduration("duration_list");
        call_duration.enqueue(new Callback<List<DurationList>>() {
            @Override
            public void onResponse(Call<List<DurationList>> call, Response<List<DurationList>> response) {
                arrayList_duration =response.body();
                for (DurationList C:arrayList_duration){
                    if (C.getTitle() != null){
                        Log.d("Duration" , C.getTitle() );
                        final String[] languageName = new String[arrayList_duration.size()];
                        for (int i=0 ; i<arrayList_duration.size() ; i++){
                            languageName[i]= arrayList_duration.get(i).getTitle();
                        }
                        MultiSelectSpinner freelancer= findViewById(R.id.ui_freelancer_level_list);
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        duration.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {


                                 durationArray = new String[arrayList_duration.size()];

                                for(int j = 0; j < arrayList_duration.size(); j++){
                                    if(selected[j]) {
                                        durationArray[j] = arrayList_duration.get(j).getValue();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<DurationList>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        // Multi selection spinner for category

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

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        category.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {


                                categoryArray = new String[arrayList_category.size()];

                                for(int j = 0; j < arrayList_category.size(); j++){
                                    if(selected[j]) {
                                        categoryArray[j] = arrayList_category.get(j).getSlug();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<ProjectCat>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection spinner for english level

        Call<List<EnglishLevel>> call_english = RetrofitClient.getInstance().getApi().getenglishlist("english_levels");
        call_english.enqueue(new Callback<List<EnglishLevel>>() {
            @Override
            public void onResponse(Call<List<EnglishLevel>> call, Response<List<EnglishLevel>> response) {
                arrayList_english =response.body();
                for (EnglishLevel C:arrayList_english){
                    if (C.getTitle() != null){
                        Log.d("English level" , C.getTitle());
                        final String[] levelName = new String[arrayList_english.size()];
                        for (int i=0 ; i<arrayList_english.size() ; i++){
                            levelName[i]= arrayList_english.get(i).getTitle();
                        }
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                levelName);

                        english.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {

                                 englishLevelArray = new String[arrayList_english.size()];

                                for(int j = 0; j < arrayList_english.size(); j++){
                                    if(selected[j]) {
                                        englishLevelArray[j] = arrayList_english.get(j).getValue();

                                    }
                                }

                            }
                        })
                                .setSelectAll(false).setMinSelectedItems(0);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<EnglishLevel>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection for languages

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

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        language.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {

                                 languagesArray = new String[arrayList_languages.size()];

                                for(int j = 0; j < arrayList_languages.size(); j++){
                                    if(selected[j]) {
                                        languagesArray[j] = arrayList_languages.get(j).getSlug();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection for skill

        Call<List<Skill>> call_skills = RetrofitClient.getInstance().getApi().getskill("skills");
        call_skills.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                arrayList_skill =response.body();
                for (Skill C:arrayList_skill){
                    if (C.getName() != null){
                        Log.d("Skills" , C.getName());
                        final String[] languageName = new String[arrayList_skill.size()];
                        for (int i=0 ; i<arrayList_skill.size() ; i++){
                            languageName[i]= arrayList_skill.get(i).getName();
                        }
                        MultiSelectSpinner skill= findViewById(R.id.ui_skill_list);
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        skill.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {
                                skillsArray = new String[arrayList_skill.size()];

                                for(int j = 0; j < arrayList_skill.size(); j++){
                                    if(selected[j]) {
                                        skillsArray[j] = arrayList_skill.get(j).getSlug();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi Selection spinner for project type

        Call<List<ProjectType>> call_type = RetrofitClient.getInstance().getApi().getProjecttype("project_type");
        call_type.enqueue(new Callback<List<ProjectType>>() {
            @Override
            public void onResponse(Call<List<ProjectType>> call, Response<List<ProjectType>> response) {
                arrayList_type =response.body();
                for (ProjectType C:arrayList_type){
                    if (C.getTitle() != null){
                        Log.d("Project Type" , C.getTitle() );
                        final String[] languageName = new String[arrayList_type.size()];
                        for (int i=0 ; i<arrayList_type.size() ; i++){
                            languageName[i]= arrayList_type.get(i).getTitle();
                        }
                        MultiSelectSpinner skill= findViewById(R.id.ui_skill_list);
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        type.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {

                                 projectTypeArray = new String[arrayList_type.size()];

                                for(int j = 0; j < arrayList_type.size(); j++){
                                    if(selected[j]) {
                                        projectTypeArray[j] = arrayList_type.get(j).getValue();

                                    }
                                }
                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<ProjectType>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection for location

        Call<List<Location>> call_location = RetrofitClient.getInstance().getApi().getlocationlist("locations");
        call_location.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                arrayList_location =response.body();
                for (Location C:arrayList_location){
                    if (C.getTitle() != null){
                        Log.d("Locations" , C.getTitle() );
                        final String[] languageName = new String[arrayList_location.size()];
                        for (int i=0 ; i<arrayList_location.size() ; i++){
                            languageName[i]= arrayList_location.get(i).getTitle();
                        }

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        location.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {


                                 locationArray = new String[arrayList_location.size()];

                                for(int j = 0; j < arrayList_location.size(); j++){
                                    if(selected[j]) {
                                        locationArray[j] = arrayList_location.get(j).getSlug();

                                    }
                                }

                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection spinner for freelancer type

        Call<List<FreelancerLevel>> call_freelancer_levl = RetrofitClient.getInstance().getApi().getFreelancerLevel("freelancer_level");
        call_freelancer_levl.enqueue(new Callback<List<FreelancerLevel>>() {
            @Override
            public void onResponse(Call<List<FreelancerLevel>> call, Response<List<FreelancerLevel>> response) {
                arrayList_freelancer =response.body();
                for (FreelancerLevel C:arrayList_freelancer){
                    if (C.getTitle() != null){
                        Log.d("Freelancer level" , C.getTitle() );
                        final String[] languageName = new String[arrayList_freelancer.size()];
                        for (int i=0 ; i<arrayList_freelancer.size() ; i++){
                            languageName[i]= arrayList_freelancer.get(i).getTitle();
                        }
                        MultiSelectSpinner freelancer= findViewById(R.id.ui_freelancer_level_list);
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        freelancer.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener(){
                            @Override
                            public void onItemsSelected(boolean[] selected) {


                                freelancerLevelArray = new String[arrayList_freelancer.size()];

                                for(int j = 0; j < arrayList_freelancer.size(); j++){
                                    if(selected[j]) {
                                        freelancerLevelArray[j] = arrayList_freelancer.get(j).getValue();

                                    }
                                }
                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<FreelancerLevel>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        //Multi selection spinner for no. of employees

        Call<List<NoOfEmploye>> call_no_of_employee = RetrofitClient.getInstance().getApi().getnoOfemployee("no_of_employes");
        call_no_of_employee.enqueue(new Callback<List<NoOfEmploye>>() {
            @Override
            public void onResponse(Call<List<NoOfEmploye>> call, Response<List<NoOfEmploye>> response) {
                arrayList_employees =response.body();
                for (NoOfEmploye C:arrayList_employees){
                    if (C.getTitle() != null){
                        Log.d("No of Employee" , C.getTitle() );
                        final String[] languageName = new String[arrayList_employees.size()];
                        for (int i=0 ; i<arrayList_employees.size() ; i++){
                            languageName[i]= arrayList_employees.get(i).getTitle();
                        }

                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SearchActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                languageName);

                        employee.setListAdapter(adapter).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {
                                 NoofEmployeeArray = new String[arrayList_employees.size()];

                                for(int j = 0; j < arrayList_employees.size(); j++){
                                    if(selected[j]) {
                                        NoofEmployeeArray[j] = arrayList_employees.get(j).getValue().toString();

                                    }
                                }
                            }
                        }).setSelectAll(false).setMinSelectedItems(0);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<NoOfEmploye>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        // simple spinner for hourly rate

        Call<List<Rate>> call_rates = RetrofitClient.getInstance().getApi().getList("rates");
        call_rates.enqueue(new Callback<List<Rate>>() {
            @Override
            public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                dd_loading.setVisibility(View.GONE);
                arrayList =response.body();
                for (Rate C:arrayList){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList.size()];
                        for (int i=0 ; i<arrayList.size() ; i++){
                            languageName[i]= arrayList.get(i).getTitle();
                        }
                        hourly_rate_list.setAdapter(new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.items_view,
                            languageName
                    ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Rate>> call, Throwable t) {
                Toast.makeText(SearchActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setlistner(){

        ui_freelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=  keyword.getText().toString().trim();
                Intent search_freelancer = new Intent(SearchActivity.this , HomeSearchResult.class);
                Bundle bundle = new Bundle();
                bundle.putString("name" , name);
                bundle.putString("rate" , keyNameStr);
                bundle.putStringArray("englishlevel",englishLevelArray);
                bundle.putStringArray("language",languagesArray);
                bundle.putStringArray("skill",skillsArray);
                bundle.putStringArray("location",locationArray);
                bundle.putStringArray("freelancerlevel",freelancerLevelArray);
                search_freelancer.putExtra(Constants.DATA, bundle);
                startActivity(search_freelancer);
            }
        });

        ui_jobs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name=  keyword.getText().toString().trim();
                Intent search_job = new Intent(SearchActivity.this , SearchResultJob.class);
                Bundle bundle = new Bundle();
                bundle.putString("name" , name);
                bundle.putStringArray("duration",durationArray);
                bundle.putStringArray("category",categoryArray);
                bundle.putStringArray("language",languagesArray);
                bundle.putStringArray("skill",skillsArray);
                bundle.putStringArray("projectType",projectTypeArray);
                bundle.putStringArray("location",locationArray);
                bundle.putStringArray("freelancerlevel",freelancerLevelArray);
                search_job.putExtra(Constants.DATA, bundle);
                startActivity(search_job);
            }
        });

        ui_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=  keyword.getText().toString().trim();
                Intent search_company = new Intent(SearchActivity.this , SearchResultCompany.class);
                Bundle bundle = new Bundle();
                bundle.putString("name" , name);
                bundle.putStringArray("location",locationArray);
                bundle.putStringArray("duration",NoofEmployeeArray);
                search_company.putExtra(Constants.DATA, bundle);
                startActivity(search_company);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
