package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import uz.itmaker.naft.BuildConfig;
import uz.itmaker.naft.Fragments.CompanyListFragment;
import uz.itmaker.naft.Fragments.FreelancerDetailFragment;
import uz.itmaker.naft.Fragments.HomeFragment;
import uz.itmaker.naft.Fragments.JobDetailFragment;
import uz.itmaker.naft.Fragments.SettingsFragment;
import uz.itmaker.naft.Model.LoginResponce.RetrofitClientLogin;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.DatabaseUtil;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity
        implements View.OnClickListener,  NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    android.support.v4.app.FragmentTransaction transaction;
    NavigationView navigationView;
    private CircleImageView pic;
    private TextView name;
    private TextView msg;
    DrawerLayout drawer;
    SweetAlertDialog pDialog;
    private static String appUrl = "https://play.google.com/store/apps/details?id=" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_search = new Intent(HomeActivity.this , SearchActivity.class);
                startActivity(move_search);
            }
        });
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        final boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(this, Constants.ISUSERLOGGEDIN);

        Fragment homefragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container ,  homefragment);
        transaction.addToBackStack(null);
        transaction.commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(getResources().getColor(R.color.drawer_color));
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        name = (TextView) header.findViewById(R.id.nav_username);
        msg = (TextView) header.findViewById(R.id.nav_user_msg);
        pic = header.findViewById(R.id.nav_profile_image);




        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        final TextView textView = (TextView) bottomNavigationView.findViewById(R.id.bottom_navigation).findViewById(R.id.largeLabel);


        if (isUserLoggedIn){
            if (user != null){
                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch(menuItem.getItemId()){

                            case R.id.action_home:
                                Fragment homefragment = new HomeFragment();
                                transaction= getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container ,  homefragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case R.id.action_jobs:
                                Fragment jobDetail = new JobDetailFragment();
                                transaction= getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container ,  jobDetail);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case R.id.action_freelancer:
                                Fragment freelancer = new FreelancerDetailFragment();
                                transaction= getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container ,  freelancer);
                                transaction.addToBackStack(null);
                                transaction.commit();

                                break;
                            case R.id.action_company:
                                Fragment company = new CompanyListFragment();
                                transaction= getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container ,  company);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case R.id.action_setting:

                                Fragment settingsFragment = new SettingsFragment();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container ,  settingsFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            default:
                                return false;
                        }
                        return true;
                    }
                });
            }
        }
        else
            {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()){

                        case R.id.action_home:
                            Fragment homefragment = new HomeFragment();
                            transaction= getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container ,  homefragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;

                        case R.id.action_jobs:
                            Fragment jobDetail = new JobDetailFragment();
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container ,  jobDetail);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;

                        case R.id.action_freelancer:
                            Fragment freelancer = new FreelancerDetailFragment();
                            transaction= getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container ,  freelancer);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;

                        case R.id.action_company:
                            Fragment company = new CompanyListFragment();
                            transaction= getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container ,  company);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;


                        case R.id.action_setting:
                            pDialog =  new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("Ой...");
                            pDialog.setContentText("Пожалуйста авторизуйтесь сначало");
                            pDialog.show();
                            break;

                        default:
                            return false;
                    }
                    return true;
                }
            });
        }

        if (isUserLoggedIn) {
            if (user != null) {

                name.setText(user.getProfile().getPmeta().getFullName());
                msg.setText(user.getProfile().getPmeta().getUserType());
                Picasso.get().load(user.getProfile().getPmeta().getProfileImg()).into(pic);

                pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                        Fragment settingsFragment = new SettingsFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container ,  settingsFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        } else {
            name.setText(getString(R.string.guest));
            msg.setText(getString(R.string.greetings));
            Picasso.get().load(R.drawable.logoapp).into(pic);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideShowitem();
    }


    public void hideShowitem(){

        RetrofitClientLogin user = DatabaseUtil.getInstance().getUser();
        boolean isUserLoggedIn = SharedPreferenceUtil.getBoolen(this, Constants.ISUSERLOGGEDIN);
            navigationView.getMenu().findItem(R.id.nav_camera).setVisible(!isUserLoggedIn);
            navigationView.getMenu().findItem(R.id.nav_setting).setVisible(isUserLoggedIn);
        if (isUserLoggedIn){
            if (user.getProfile().getPmeta().getUserType().equals("employer")){
                navigationView.getMenu().findItem(R.id.nav_post_job).setVisible(true);
            }else {
                navigationView.getMenu().findItem(R.id.nav_post_job).setVisible(false);
            }
        }else {
            navigationView.getMenu().findItem(R.id.nav_post_job).setVisible(false);
        }
            navigationView.getMenu().findItem(R.id.nav_fav).setVisible(isUserLoggedIn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode , resultCode , data);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getPrevious();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
    private void getPrevious() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        new SweetAlertDialog(HomeActivity.this , SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to go back!")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent move = new Intent(HomeActivity.this , LoginActivity.class);
            startActivity(move);
            onBackPressed();
            // Handle the camera action
        }
        else if(id == R.id.nav_fav){
            Intent move = new Intent(HomeActivity.this , FavoriteActivity.class);
            startActivity(move);
            onBackPressed();
        }
        else if(id == R.id.nav_post_job){
            Intent move = new Intent(HomeActivity.this , PostJobActivity.class);
            startActivity(move);
            onBackPressed();
        }
        else if(id == R.id.about_us){
            openWebview(Constants.ABOUTUS, getString(R.string.about_us));
        }
        else if(id == R.id.ui_rate_us){
            showRateDialog(HomeActivity.this);
        }
        else if(id == R.id.nav_invite){
            shareApp();
        }
        else if(id == R.id.nav_Contact_support){
            openWebview(Constants.CONTACTUS , getString(R.string.contact_us));
        }

        else if (id == R.id.nav_setting){

            onBackPressed();
            confirmLogoutApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void openWebview(String url,String title){
        AppUtils.openWebview(this,url,title);
    }
    protected void shareApp() {
        String message = appUrl + BuildConfig.APPLICATION_ID;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Выбрать "));
    }

    private void hideItem()
    {

    }


    @Override
    public void onClick(View v) {

    }
}
