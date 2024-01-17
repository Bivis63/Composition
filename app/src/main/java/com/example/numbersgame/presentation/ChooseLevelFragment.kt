package com.example.numbersgame.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numbersgame.R
import com.example.numbersgame.databinding.FragmentChooseLevelBinding
import com.example.numbersgame.domain.models.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
                Log.d("Gamescreen", "TEST")
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASE)
                Log.d("Gamescreen", "EASE")
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
                Log.d("Gamescreen", "NORMAL")
            }
            buttonLevelHard.setOnClickListener {

                launchGameFragment(Level.HARD)
                Log.d("Gamescreen", "HARD")
            }
        }
    }

    private fun launchGameFragment(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }

    }

}