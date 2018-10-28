package com.sergio.android.catsigar;

import android.content.Intent;
import android.os.Bundle;

import com.cuneytayyildiz.onboarder.OnboarderActivity;
import com.cuneytayyildiz.onboarder.OnboarderPage;
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntroActivity extends OnboarderActivity implements OnboarderPageChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<OnboarderPage> pages = Arrays.asList(
                new OnboarderPage.Builder()
                        .title("Donut")
                        .description("Android 1.6")
                        .imageResourceId( R.drawable.prueba2)
                        .backgroundColor(R.color.black_transparent)
                        .titleColor(R.color.white)
                        .descriptionColor(R.color.white)
                        .multilineDescriptionCentered(true)
                        .build(),

                // No need to write all of them :P

                new OnboarderPage.Builder()
                        .title("Pagina 2!!!!!!!!!!")
                        .description("Android 9.0")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.black_transparent)
                        .titleColor(R.color.white)
                        .descriptionColor(R.color.white)
                        .multilineDescriptionCentered(true)
                        .build()
        );
        setSkipButtonTitle("Saltar");
        setFinishButtonTitle("Finalizar");
        setOnboarderPageChangeListener(this);
        initOnboardingPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {

        Intent intent = new Intent(IntroActivity.this, Mod_geo.class);
        startActivity(intent);

    }

    @Override
    public void onPageChanged(int position) {

    }

    @Override
    public void onSkipButtonPressed() {
        Intent intent = new Intent(IntroActivity.this, Mod_geo.class);
        startActivity(intent);
    }
}
