package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.Constants;
import uz.itmaker.naft.Utils.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideStatusBar();


        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2*1000);
                    loader_animation();
                    if(!SharedPreferenceUtil.getBoolen(SplashActivity.this, Constants.ISUSERLOGGEDIN)) {
                        sleep(1*1000);
                        loader_animation();
                        startActivity(new Intent(SplashActivity.this, WizardActivity.class));
                    }else{
                        sleep(1*1000);
                        loader_animation();
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                    finish();
                }catch (Exception e){

                }
            }
        };
        thread.start();
    }

    public void hideStatusBar(){
        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void loader_animation(){
        TrailingCircularDotsLoader trailingCircularDotsLoader = new TrailingCircularDotsLoader(
                this,
                8,
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                100,
                5);
        trailingCircularDotsLoader.setAnimDuration(900);
        trailingCircularDotsLoader.setAnimDelay(100);

    }
}
