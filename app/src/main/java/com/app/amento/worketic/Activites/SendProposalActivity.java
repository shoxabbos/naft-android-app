package uz.itmaker.naft.Activites;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import uz.itmaker.naft.Adapters.MyAdapter;
import uz.itmaker.naft.Interface.RetrofitClient;
import uz.itmaker.naft.Model.TaxonomyListing.DurationList;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.FileUtils;
import uz.itmaker.naft.view.InternetConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendProposalActivity extends AppCompatActivity {

    private static final String TAG = SendProposalActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    private EditText amount , content;
    private TextView amount_for_company , amount_for_you;
    private Spinner spinner;
    List<DurationList> arrayList_duration;
    ProgressBar dd_loading;
    private View parentView;
    private ListView listView;
    private Button btnChoose, btnUpload;
    private ArrayList<Uri> arrayList;
    private TextView sample;
    RequestBody proposed_amount;
    RequestBody proposed_content;
    RequestBody size;
    String keyNameStr;
    RequestBody proposed_time;
    String user_id;
    String project_id;
    int company_amount;
    Bundle bundle;
    SweetAlertDialog pDialog;
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_proposal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ui_app_bar_send_proposal);
        setSupportActionBar(toolbar);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        arrayList = new ArrayList<>();
        bundle = getIntent().getBundleExtra(Constants.DATA);

        initData();
        setData();

    }

    private  void initData(){

        spinner = findViewById(R.id.ui_list_spinner);
        listView = findViewById(R.id.list_view);
        setListViewHeightBasedOnChildren(listView);
        dd_loading = findViewById(R.id.ui_bookloading);
        btnUpload = findViewById(R.id.ui_send);
        btnChoose = findViewById(R.id.ui_select_file);
        amount = findViewById(R.id.ui_amount);
        content = findViewById(R.id.ui_content);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if (arrayList_duration != null && arrayList_duration.size() != 0){
                    keyNameStr = arrayList_duration.get(position).getValue();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

    }
    private void setData(){
        dd_loading.setVisibility(View.VISIBLE);
        Call<List<DurationList>> call_duration = RetrofitClient.getInstance().getApi().getduration("duration_list");
        call_duration.enqueue(new Callback<List<DurationList>>() {
            @Override
            public void onResponse(Call<List<DurationList>> call, Response<List<DurationList>> response) {
                dd_loading.setVisibility(View.GONE);
                arrayList_duration =response.body();
                for (DurationList C:arrayList_duration){
                    if (C.getTitle() != null){
                        Log.d("Duration" , C.getTitle() );
                        final String[] languageName = new String[arrayList_duration.size()];
                        for (int i=0 ; i<arrayList_duration.size() ; i++){
                            languageName[i]= arrayList_duration.get(i).getTitle();
                        }
                        ArrayAdapter<String> adapter= new ArrayAdapter <String>(SendProposalActivity.this,
                                R.layout.items_view,
                                languageName);

                        spinner.setAdapter(adapter);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<DurationList>> call, Throwable t) {
                Toast.makeText(SendProposalActivity.this , t.getMessage() , Toast.LENGTH_SHORT).show();
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

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImagesToServer();
            }
        });
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
                                MyAdapter mAdapter = new MyAdapter(SendProposalActivity.this, arrayList);
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
                            MyAdapter mAdapter = new MyAdapter(SendProposalActivity.this, arrayList);
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
    private void uploadImagesToServer() {
        if (InternetConnection.checkConnection(SendProposalActivity.this)) {


            showProgress();

            List<MultipartBody.Part> attachments = new ArrayList<>();

            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    attachments.add(prepareFilePart("attachments"+i, arrayList.get(i)));
                }
            }

             user_id = DatabaseUtil.getInstance().getUser().getProfile().getUmeta().getId();
             project_id = bundle.getString("project_id");
             proposed_amount = createPartFromString(amount.getText().toString()) ;
             proposed_time = createPartFromString(keyNameStr);
             proposed_content = createPartFromString(content.getText().toString().trim());
             size = createPartFromString(""+attachments.size());


            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadMultiple(
                    user_id , project_id , proposed_amount , proposed_time , proposed_content ,
                    size , attachments);

            call.enqueue(new Callback<ResponseBody>(){
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response){
                    hideProgress();
                    if (response.code() ==200){

                        pDialog = new SweetAlertDialog(SendProposalActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Good job!");
                        pDialog.setContentText("Proposal submitted successfully!");
                        pDialog.show();

                    }else if(response.code() ==203) {

                        pDialog =  new SweetAlertDialog(SendProposalActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Ой...");
                        pDialog.setContentText("Sorry, \n You are not authorized to perform this action.");
                        pDialog.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    hideProgress();
                    pDialog =  new SweetAlertDialog(SendProposalActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Ой...");
                    pDialog.setContentText("There is some network error.");
                    pDialog.show();
                }
            });

        } else {
            hideProgress();
            Toast.makeText(SendProposalActivity.this,
                    R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri){

        File attachments = FileUtils.getFile(this, fileUri);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        attachments
                );
        return MultipartBody.Part.createFormData(partName, attachments.getName(), requestFile);
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
            int hasCallPermission = ContextCompat.checkSelfPermission(SendProposalActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SendProposalActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(SendProposalActivity.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                } else {
                    ActivityCompat.requestPermissions(SendProposalActivity.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(SendProposalActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(SendProposalActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(SendProposalActivity.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showChooser();
                } else {
                    Toast.makeText(SendProposalActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }
    private void showProgress() {
        dd_loading.setVisibility(View.VISIBLE);
        btnChoose.setEnabled(false);
        btnUpload.setEnabled(false);
    }

    private void hideProgress() {
        dd_loading.setVisibility(View.GONE);
        btnChoose.setEnabled(true);
        btnUpload.setEnabled(true);
    }
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        return true;
    }

}
