package fi.ksoamk.potatosaucer3000.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import fi.ksoamk.potatosaucer3000.MainActivity
import fi.ksoamk.potatosaucer3000.PTTSaucer
import fi.ksoamk.potatosaucer3000.R


private const val ARG_PARAM1 = "newscore"


class ScoreFragment : Fragment() {

    private var lastscore: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lastscore = it.getInt(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val scores = PTTSaucer.prefs.getString("scores", "0,0,0,0,0")

        val view: View = inflater.inflate(R.layout.fragment_score, container, false)
        val menubtn1 = view.findViewById<Button>(R.id.btnBack1)
        val menubtn2 = view.findViewById<Button>(R.id.btnBack2)
        val highscores = view.findViewById<TextView>(R.id.highscores)

        val scoresfromemory = scores?.removeSurrounding("[","]")?.split(",")?.map {it.toInt()}
        val sortedscores = scoresfromemory?.sortedDescending()

        val newhighscores =
                "1st ${sortedscores!![0]}\n\n" +
                "2nd ${sortedscores[1]}\n\n" +
                "3rd ${sortedscores[2]}\n\n" +
                "4th ${sortedscores[3]}\n\n" +
                "5th ${sortedscores[4]}"

        highscores.text = newhighscores

        menubtn1?.setOnClickListener{
            parentFragmentManager.beginTransaction().remove(this).commit()
            (context as MainActivity).changeFragment(MainFragment())

        }
        menubtn2?.setOnClickListener{
            parentFragmentManager.beginTransaction().remove(this).commit()
            (context as MainActivity).changeFragment(MainFragment())

        }
        // Inflate the layout for this fragment

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param newscore Parameter 1.
         * @return A new instance of fragment ScoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(newscore: Int) =
                ScoreFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, newscore)
                    }
                }
    }
}