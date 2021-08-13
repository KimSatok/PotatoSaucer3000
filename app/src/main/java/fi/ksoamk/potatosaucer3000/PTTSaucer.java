package fi.ksoamk.potatosaucer3000;

import android.app.Application;
import android.content.SharedPreferences;

public class PTTSaucer extends Application {
    public static SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = getSharedPreferences(getPackageName() + "_prefs", MODE_PRIVATE);
    }
}
