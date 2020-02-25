package uz.itmaker.naft.Activites;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uz.itmaker.naft.Adapters.LatestJobAttachmentAdapter;
import uz.itmaker.naft.Adapters.LatestJonSkillsAdapter;
import uz.itmaker.naft.Model.Latestjob.Attanchent;
import uz.itmaker.naft.Model.Latestjob.LatestJobModel;
import uz.itmaker.naft.Model.Latestjob.Skill;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailJobActivity extends BaseActivity {
    DownloadManager downloadManager;
    int i;
    private Button send_proposal;
    private LatestJobModel latest;
    SweetAlertDialog pDialog;
    Toolbar toolbar;
    private ImageView share_job , report;
    private ShimmerRecyclerView attacment_view , skills_view;
    private LinearLayout atachment_layout , skill_layout;
    private TextView wt_latest_name , wt_latest_title, wt_latest_carrer_level ,
            wt_latest_location , wt_latest_job_type ,wt_latest_duration ,
            wt_latest_detail , wt_latest_amount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);
        toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_job);
        setSupportActionBar(toolbar);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        latest = (LatestJobModel) getIntent().getSerializableExtra(Constants.DATA);
        initData();
        setData();
        setAdapter();
    }


    public void initData(){

        wt_latest_name = findViewById(R.id.ui_latest_job_name);
        wt_latest_carrer_level = findViewById(R.id.ui_latest_carrer_level);
        wt_latest_title = findViewById(R.id.ui_latest_job_title);
        wt_latest_location = findViewById(R.id.ui_latest_job_country_name);
        wt_latest_job_type = findViewById(R.id.ui_latest_job_type);
        wt_latest_duration = findViewById(R.id.ui_latest_job_duration);
        wt_latest_detail = findViewById(R.id.ui_latest_job_detail);
        wt_latest_amount = findViewById(R.id.ui_latest_job_amount);
        atachment_layout = findViewById(R.id.ui_attachment_layout);
        skill_layout = findViewById(R.id.ui_skill_layout_latest);
        send_proposal = findViewById(R.id.ui_send_proposal);
        share_job = findViewById(R.id.ui_share);
        report = findViewById(R.id.ui_Report);
        attacment_view = findViewById(R.id.latest_job_attatchment);
        attacment_view.setLayoutManager(new LinearLayoutManager(DetailJobActivity.this , LinearLayout.HORIZONTAL , false));

        ShimmerRecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        skills_view = findViewById(R.id.skills_recycler_view);
        skills_view.setHasFixedSize(true);
        skills_view.setLayoutManager(layoutManager);

        share_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharejob();
            }
        });


        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(DetailJobActivity.this, Constants.ISUSERLOGGEDIN);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUserLoggedIn){
                    openDialogActivity();
                }else {

                    pDialog =  new SweetAlertDialog(DetailJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                    pDialog.show();
                }

            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(DetailJobActivity.this , SearchActivity.class);
                startActivity(move);
            }
        });
    }

    public void setData(){
        wt_latest_name.setText(Html.fromHtml(latest.getEmployerName()).toString());
        wt_latest_title.setText(Html.fromHtml(latest.getProjectTitle()).toString());
        wt_latest_carrer_level.setText(Html.fromHtml(latest.getProjectLevel().getLevelTitle()).toString());
        wt_latest_location.setText(Html.fromHtml(latest.getLocation().getCountry()).toString());
        wt_latest_job_type.setText(Html.fromHtml(latest.getProjectType()).toString());
        wt_latest_duration.setText(Html.fromHtml(latest.getProjectDuration()).toString());
        wt_latest_detail.setText(Html.fromHtml(latest.getProjectContent()).toString());
        wt_latest_amount.setText(Html.fromHtml(latest.getAmount()).toString());

        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(DetailJobActivity.this, Constants.ISUSERLOGGEDIN);

        if (isUserLoggedIn){

            send_proposal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();

                        if (user.getProfile().getPmeta().getUserType().equals("employer")){
                            pDialog =  new SweetAlertDialog(DetailJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("Ой...");
                            pDialog.setContentText("You are restricted");
                            pDialog.show();

                        }
                        if (user.getProfile().getPmeta().getUserType().equals("freelancer")) {

                            if (latest.getStatus().equals("posted")){
                                Intent move = new Intent(DetailJobActivity.this , SendProposalActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("project_id" , latest.getJobId().toString());
                                move.putExtra(Constants.DATA , bundle);
                                startActivity(move);
                            } else {
                                pDialog =  new SweetAlertDialog(DetailJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitleText("Ой...");
                                pDialog.setContentText("The job is already hired");
                                pDialog.show();

                            }
                        }
                }
            });
            }else {

            send_proposal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pDialog =  new SweetAlertDialog(DetailJobActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                    pDialog.show();
                }
            });
            }
    }

    public void setAdapter(){

        List<Attanchent> attanchents =  latest.getAttanchents();
        if (attanchents != null && !attanchents.isEmpty()) {
            attacment_view.setAdapter(new LatestJobAttachmentAdapter(attanchents , null , DetailJobActivity.this));
        }else {
            atachment_layout.setVisibility(View.GONE);
        }

        List<Skill> skill = latest.getSkills();
        if (skill != null && !skill.isEmpty()){
            skills_view.setAdapter(new LatestJonSkillsAdapter(skill , null));
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

    protected void sharejob() {
        String message = latest.getLink();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Choose" ));
    }
    public void openDialogActivity(){

        Intent move = new Intent(DetailJobActivity.this , JobReportActivity.class);


        Bundle bundle = new Bundle();
        bundle.putInt("id" , latest.getJobId());

        move.putExtra(Constants.DATA, bundle);
        startActivity(move);


    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
