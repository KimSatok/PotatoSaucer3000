package fi.ksoamk.potatosaucer3000.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fi.ksoamk.potatosaucer3000.GameActivity
import fi.ksoamk.potatosaucer3000.MainActivity
import fi.ksoamk.potatosaucer3000.R
import kotlin.system.exitProcess


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.main_fragment, container, false)

        val intent = Intent(activity, GameActivity::class.java)
        val playbtn = view.findViewById<Button>(R.id.btnPlay)
        val scorebtn = view.findViewById<Button>(R.id.btnHighscore)
        val quitbtn = view.findViewById<Button>(R.id.btnQuit)
        //play button
        playbtn.setOnClickListener{
            startActivity(intent)
        }
        //highscore button
        scorebtn.setOnClickListener{
            (context as MainActivity).changeFragment(ScoreFragment.newInstance(1))
        }
        //quit button
        quitbtn.setOnClickListener{
            exitProcess(0)
        }

        return view
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}