package com.sergio.android.catsigar;

import android.content.Intent;
import android.graphics.Color;
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
                        .title("Bienvenido al tutorial de CatSIGAR")
                        .description("En este tutorial aprenderás a utilizar esta herramienta que " +
                                "mezcla elementos de Sistemas de información Geográfica y Realidad Aumentada")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.black_transparent)
                        .titleColor(R.color.Black)
                        .descriptionColor(R.color.Black)
                        .multilineDescriptionCentered(true)
                        .build(),

                // No need to write all of them :P

                new OnboarderPage.Builder()
                        .title("Conociendo la interfaz")
                        .description("CatSIGAR tiene una interfaz muy sencilla que evita confundir al " +
                                "usuario con su uso, tiene un botón que hace una solicitud a un geoservicio (1)," +
                                " botones de navegación (2)(3) y la posibilidad de cambiar los mapas base (4).")
                        .imageResourceId( R.drawable.p1)
                        .backgroundColor(R.color.P2)
                        .titleColor(R.color.P2_t)
                        .descriptionColor(R.color.P2_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Haz un acercamiento y toca el botón para solicitar placas")
                        .description("Navega por el mapa y acerca utilizando los dos dedos al tiempo (1) " +
                                "o usando los botones de navegación (2). Una vez estés visualizando una " +
                                "manzana solamente, toca el botón 'Solicitar Placas Domiciliarias (3)' ")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P3)
                        .titleColor(R.color.P3_t)
                        .descriptionColor(R.color.P3_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Mueve un poco la vista y observa")
                        .description("Saldrán unos puntos o marcadores, estas son las placas " +
                                "domiciliarias (1), posiblemente algunas se agrupen (2), solo debes " +
                                "acercarte un poco más para verlas")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P4)
                        .titleColor(R.color.P4_t)
                        .descriptionColor(R.color.P4_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Toca los marcadores")
                        .description("Al tocar los marcadores saldrá la información del número de placa" +
                                "y el código predial de la construcción (1), esto te permitirá identificar" +
                                " la construcción asociada, una vez sepas cuál es la construcción que deseas" +
                                "'aumentar' toca nuevamente sobre el globito y de esta manera se abrirá el" +
                                "módulo de Realidad Aumentada.")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P5)
                        .titleColor(R.color.P5_t)
                        .descriptionColor(R.color.P5_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Módulo de Realidad Aumentada")
                        .description("En este módulo el cual se basa en el SDK de detección de objetos desarrollado" +
                                "por la compañía Wikitude, podrás observar la construcción con su modelo 3D asociado," +
                                "conocerás información que tal vez no veas de forma directa y así mismo," +
                                "conseguirás evaluar si la misma no tiene variaciones respecto a su diseño" +
                                "original")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P6)
                        .titleColor(R.color.P6_t)
                        .descriptionColor(R.color.P6_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Escanea la construcción")
                        .description("Al abrir el módulo de Realidad Aumentada observarás que te dice 'escanea la construcción' (1)" +
                                "y verás todo lo que la cámara del dispositivo te muestre, debes apuntar con tu cámara" +
                                "hacia la construcción que decidiste aumentar, esta debe ser exactamente la misma" +
                                " correspondiente a la placa domiciliaria del predio. Haz un paneo y aparecerá el modelo (2)")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P7)
                        .titleColor(R.color.P7_t)
                        .descriptionColor(R.color.P7_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("Toca la aumentación para ver más datos")
                        .description("Si tocas la aumentación se abrirá una nueva ventana en la cual encontrarás" +
                                " aún más información asociada al predio visualizándolo en forma de tabla")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P8)
                        .titleColor(R.color.P8_t)
                        .descriptionColor(R.color.P8_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("¿Qué sucede si el modelo no existe?")
                        .description("Actualmente la aplicación tiene solo unos pocos modelos, por lo cual" +
                                " no se encuentran todas las construcciones, cuando esto sucede la aplicación" +
                                " te informará de lo que sucede y te dirá que pruebes con una muestra de una pirámide," +
                                " de esta manera podrás conocer las capacidades de la aplicación y una información asociada.")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.P9)
                        .titleColor(R.color.P9_t)
                        .descriptionColor(R.color.P9_t)
                        .multilineDescriptionCentered(true)
                        .build(),
                new OnboarderPage.Builder()
                        .title("¡Listo! Haz completado el tutorial")
                        .description("Ya puedes iniciar la aplicación y empezar a usarla, bienvenido nuevamente a " +
                                "CatSIGAR")
                        .imageResourceId( R.drawable.web_hi_res_512)
                        .backgroundColor(R.color.black_transparent)
                        .titleColor(R.color.Black)
                        .descriptionColor(R.color.Black)
                        .multilineDescriptionCentered(true)
                        .build()
        );
        setSkipButtonTextColor(R.color.Black);
        setSkipButtonTitle("Saltar");
        setFinishButtonTextColor(R.color.Black);
        setFinishButtonTitle("Finalizar");
        setOnboarderPageChangeListener(this);
        initOnboardingPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {

        Intent intent = new Intent(IntroActivity.this, Mod_geo.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageChanged(int position) {

    }

    @Override
    public void onSkipButtonPressed() {
        Intent intent = new Intent(IntroActivity.this, Mod_geo.class);
        startActivity(intent);
        finish();
    }
}
