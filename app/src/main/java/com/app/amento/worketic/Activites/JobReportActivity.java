package uz.itmaker.naft.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.TaxonomyListing.Reason;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobReportActivity extends BaseActivity {


    private Spinner reason;
    private EditText reason_Detail;
    private Button ui_send_report;
    int id;
    List<Reason> arrayList;
    String user_id;
    String keyNameStr;
    String description;
    SweetAlertDialog pDialog;
    ProgressBar dd_loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_detail_app_bar_report_job);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dd_loading = findViewById(R.id.ui_bookloading);
        reason = findViewById(R.id.ui_spinner_report);
        reason_Detail = findViewById(R.id.ui_reson_detail);
        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (bundle != null){
            id = bundle.getInt("id");
        }
        setData();
    }


    private void setData(){
        dd_loading.setVisibility(View.VISIBLE);
        user_id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
        ui_send_report = findViewById(R.id.ui_send_report);


        Call<List<Reason>> call_rates = RetrofitClient.getInstance().getApi().getReason("reason_type");
        call_rates.enqueue(new Callback<List<Reason>>() {
            @Override
            public void onResponse(Call<List<Reason>> call, Response<List<Reason>> response) {
                dd_loading.setVisibility(View.GONE);
                arrayList =response.body();
                for (Reason C:arrayList){
                    if (C.getTitle() != null){
                        Log.d("Rates" , C.getTitle() );
                        final String[] languageName = new String[arrayList.size()];
                        for (int i=0 ; i<arrayList.size() ; i++){
                            languageName[i]= arrayList.get(i).getTitle();
                        }
                        reason.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.items_view,
                                languageName
                        ));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Reason>> call, Throwable t) {
                Toast.makeText(JobReportActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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




        ui_send_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                description = reason_Detail.getText().toString().trim();

                Call<ResponseBody> call_report = RetrofitClient.getInstance().getApi().ReportUser(user_id ,id ,  keyNameStr ,description );
                call_report.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200){

                            pDialog = new SweetAlertDialog(JobReportActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.setTitleText("Молодец!");
                            pDialog.setContentText("Отчет успешно отправлен!");
                            pDialog.show();

                        }else{
                            pDialog =  new SweetAlertDialog(JobReportActivity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("Ой...");
                            pDialog.setContentText("Вы ограничены");
                            pDialog.show();
                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
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
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }
}
