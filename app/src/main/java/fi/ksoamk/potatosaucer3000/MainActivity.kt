package fi.ksoamk.potatosaucer3000

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fi.ksoamk.potatosaucer3000.ui.main.MainFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val checkscores = PTTSaucer.prefs.getString("scores", "1")
        if (checkscores == "1"){
            PTTSaucer.prefs.edit().putString("scores", "21,1,15,20,4").apply()

        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MainFragment.newInstance()
            ).commitNow()
        }
    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

}