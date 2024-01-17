package com.example.numbersgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numbersgame.R
import com.example.numbersgame.databinding.FragmentGameBinding
import com.example.numbersgame.domain.models.GameResult
import com.example.numbersgame.domain.models.GameSettings
import com.example.numbersgame.domain.models.Level
import java.io.Serializable
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameResult = GameResult(
            true,
            11,
            2,
            GameSettings(11, 2, 5, 7)
        )

        binding.tvSum.setOnClickListener {
            launchGameFinishedFragment(gameResult)
        }
    }

    private fun launchGameFinishedFragment(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val NAME = "game_fragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

}