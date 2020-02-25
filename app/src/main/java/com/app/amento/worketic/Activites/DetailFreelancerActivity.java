package uz.itmaker.naft.Activites;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uz.itmaker.naft.Adapters.ProviderEducationAdapter;
import uz.itmaker.naft.Adapters.ProviderExperienceAdapter;
import uz.itmaker.naft.Adapters.ProviderReviewAdapter;
import uz.itmaker.naft.Adapters.ProviderSkillsAdapter;
import uz.itmaker.naft.Adapters.ProviderprojectsAdapter;
import uz.itmaker.naft.Adapters.providerAwardceAdapter;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.Model.Provider.Award;
import uz.itmaker.naft.Model.Provider.Education;
import uz.itmaker.naft.Model.Provider.Experience;
import uz.itmaker.naft.Model.Provider.Project;
import uz.itmaker.naft.Model.Provider.ProviderModel;
import uz.itmaker.naft.Model.Provider.Review;
import uz.itmaker.naft.Model.Provider.Skill;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailFreelancerActivity extends BaseActivity {

    private ShimmerRecyclerView recyclerView , recyclerView_Review , recyclerView_projects , recyclerView_experience , recyclerView_education , recyclerView_skill;
    private LinearLayout award_layout , review_layout , projects_lauout , experience_layout , education_layout , skill_layout;
    private ProviderModel featured;
    private Button offer_button;
    SweetAlertDialog pDialog;
    Toolbar toolbar;
    RetrofitClientLogin user;
    private ImageView wt_profile_image , wt_banner ,  badge_color , badge_icon, ui_share;
    private TextView wt_name , wt_title , wt_hourly_rate , wt_country_name, wt_feedback , wt_member_since , wt_about ,
                     wt_ongoing , wt_completed , wt_cancelled, ui_total_earning;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_freelancer);
        toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar);
        setSupportActionBar(toolbar);
        user = DatabaseUtil.getInstance().getUser();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        featured = (ProviderModel) getIntent().getSerializableExtra(Constants.DATA);
        initdata();
        setData();

        setAdapter();
    }

    private void initdata(){

        wt_name = findViewById(R.id.ui_freelancer_detail_name);
        wt_title = findViewById(R.id.ui_freelancer_detail_title);
        wt_hourly_rate = findViewById(R.id.ui_freelancer_detail_hourly_rate);
        wt_member_since = findViewById(R.id.ui_freelancer_detail_member_since);
        wt_country_name = findViewById(R.id.ui_freelancer_detail_country_name);
        wt_feedback = findViewById(R.id.ui_freelancer_detail_feedback);
        wt_about = findViewById(R.id.ui_freelancer_detail_about);
        wt_banner = findViewById(R.id.ui_freelancer_detail_banner);
        wt_profile_image = findViewById(R.id.detail_profile_image);
        wt_ongoing = findViewById(R.id.ui_freelancer_detail_ongoing_projects);
        wt_completed = findViewById(R.id.ui_freelancer_detail_completed_projects);
        offer_button = findViewById(R.id.ui_send_offer_freelancer);
        wt_cancelled = findViewById(R.id.ui_freelancer_detail_cancelled_projects);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        badge_color = findViewById(R.id.ui_freelancer_badge_color);
        badge_icon = findViewById(R.id.ui_freelancer_badge_icon);
        ui_share = findViewById(R.id.ui_share_freelancer);
        ui_total_earning = findViewById(R.id.total_earning);
        recyclerView_Review = findViewById(R.id.list_review);
        recyclerView_Review.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Review.setNestedScrollingEnabled(false);
        recyclerView_projects = findViewById(R.id.list_projects);
        recyclerView_projects.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_projects.setNestedScrollingEnabled(false);

        recyclerView_experience = findViewById(R.id.list_experience);
        recyclerView_experience.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_experience.setNestedScrollingEnabled(false);

        recyclerView_education = findViewById(R.id.list_education);
        recyclerView_education.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_education.setNestedScrollingEnabled(false);

        recyclerView_skill = findViewById(R.id.list_skill);
        recyclerView_skill.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_skill.setNestedScrollingEnabled(false);

        award_layout = findViewById(R.id.uiaward_layout);
        projects_lauout = findViewById(R.id.ui_projects_layout);
        review_layout = findViewById(R.id.ui_review_layout);
        experience_layout = findViewById(R.id.ui_experience_layout);
        education_layout = findViewById(R.id.ui_education_layout);
        skill_layout = findViewById(R.id.ui_skill_layout);
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(DetailFreelancerActivity.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){
            offer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getProfile().getPmeta().getUserType().equals("freelancer")){
                        pDialog =  new SweetAlertDialog(DetailFreelancerActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Ой...");
                        pDialog.setContentText("You are restricted to perform that action");
                        pDialog.show();
                    }else if(user.getProfile().getPmeta().getUserType().equals("employer")){
                        openDialogActivity();
                    }

                }
            });
        }else {
            offer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pDialog =  new SweetAlertDialog(DetailFreelancerActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                    pDialog.show();
                }
            });
        }
        ui_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFreelancer();
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(DetailFreelancerActivity.this , SearchActivity.class);
                startActivity(move);
            }
        });
    }

    public void setData(){

        Picasso.get().load(featured.getProfileImg()).into(wt_profile_image);
        Picasso.get().load(featured.getBannerImg()).into(wt_banner);
        wt_name.setText(Html.fromHtml(featured.getName()).toString());
        wt_title.setText(Html.fromHtml(featured.getTagLine()).toString());
        if(featured.getTotalEarnings().equals("")){
            ui_total_earning.setText("0.00$");
        }else {
            ui_total_earning.setText(Html.fromHtml(featured.getTotalEarnings()).toString());
        }

        wt_hourly_rate.setText(Html.fromHtml(featured.getPerhourRate()).toString());
        wt_country_name.setText(Html.fromHtml(featured.getLocation().getCountry()).toString());
        wt_member_since.setText(Html.fromHtml(featured.getMemberSince()).toString());
        if (featured.getWtAverageRating().equals(0)){
            wt_feedback.setText("Нет отзывов");
        }else {

            wt_feedback.setText(featured.getWtAverageRating() + "/5 (" + featured.getWtTotalRating()+ " " + "Feedback)");
        }
        wt_about.setText(Html.fromHtml(featured.getContent()).toString());
        wt_ongoing.setText(Integer.toString(featured.getOngoningJobs()));
        wt_completed.setText(Integer.toString(featured.getCompletedJobs()));
        wt_cancelled.setText(Integer.toString(featured.getCancelledJobs()));

        if (featured.getBadge().getBadgetColor().equals("")){
            badge_color.setVisibility(View.GONE);
            badge_icon.setVisibility(View.GONE);
        }else {
            Picasso.get().load(featured.getBadge().getBadgetUrl()).into(badge_icon);
            badge_icon.setVisibility(View.VISIBLE);
            badge_color.setVisibility(View.VISIBLE);
        }

    }

    protected void setAdapter() {
        List<Award> Awards =  featured.getAwards();
        if (Awards != null && !Awards.isEmpty()) {
            recyclerView.setAdapter(new providerAwardceAdapter(Awards , null));
        }else {
            award_layout.setVisibility(View.GONE);
        }


        List<Review> Reviews = featured.getReviews();
        if (Reviews != null && !Reviews.isEmpty()) {
            recyclerView_Review.setAdapter(new ProviderReviewAdapter(Reviews , null));
        }else {
            review_layout.setVisibility(View.GONE);
        }

        List<Project> projecs = featured.getProjects();
        if (projecs != null && !projecs.isEmpty()) {
            recyclerView_projects.setAdapter(new ProviderprojectsAdapter(projecs , null));
        }else {
            projects_lauout.setVisibility(View.GONE);
        }


        List<Experience> experience = featured.getExperience();
        if (experience != null && !experience.isEmpty()) {
            recyclerView_experience.setAdapter(new ProviderExperienceAdapter(experience , null));
        }else {
            experience_layout.setVisibility(View.GONE);
        }

        List<Education> educations = featured.getEducations();
        if (educations != null && !educations.isEmpty()) {
            recyclerView_education.setAdapter(new ProviderEducationAdapter(educations , null));
        }else {
            education_layout.setVisibility(View.GONE);
        }

        List<Skill> skill = featured.getSkills();
        if (skill != null && !skill.isEmpty()) {
            recyclerView_skill.setAdapter(new ProviderSkillsAdapter(skill , null));
        }else {
            skill_layout.setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }

    public void openDialogActivity(){

        Intent move = new Intent(DetailFreelancerActivity.this , SendOffer.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id" , featured.getUserId());
        move.putExtra(Constants.DATA, bundle);
        startActivity(move);

    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
    protected void shareFreelancer() {
        String message = featured.getFreelancerLink();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Choose" ));
    }
}
