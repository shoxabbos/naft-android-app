package uz.itmaker.naft.Activites;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uz.itmaker.naft.Adapters.WizardImageAdapter;
import uz.itmaker.naft.R;
import uz.itmaker.naft.Utils.AppUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WizardActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout slideDotspanel;
    private TextView wt_skip;
    int i;

    private int dotcounts;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_wizard);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        slideDotspanel = (LinearLayout) findViewById(R.id.ui_slide_dots);
        wt_skip = (TextView) findViewById(R.id.ui_skip_button);
        viewPager = findViewById(R.id.view_pager);
        WizardImageAdapter adapter = new WizardImageAdapter(WizardActivity.this);
        viewPager.setAdapter(adapter);

        dotcounts = adapter.getCount();
        dots = new ImageView[dotcounts];

        for(i = 0; i < dotcounts; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            slideDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                for (i=0 ; i< dotcounts ; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        wt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(WizardActivity.this , HomeActivity.class);
                startActivity(move);

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyImageTimer() , 2000 , 3000);
    }

    public void hideStatusBar() {
        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }

    public class MyImageTimer extends TimerTask{


        @Override
        public void run() {
            WizardActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
