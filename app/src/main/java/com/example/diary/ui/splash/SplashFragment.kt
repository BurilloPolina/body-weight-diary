package com.example.diary.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.diary.R
import com.example.diary.databinding.FragmentSplashBinding
import com.example.diary.model.prefs.Prefs
import com.google.gson.Gson

class SplashFragment : Fragment(R.layout.fragment_splash) {

    lateinit var binding: FragmentSplashBinding

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this, SplashViewModelFactory(createPrefs()))
            .get(SplashViewModel::class.java)

        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_mainFragment)
            } else {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_startFragment)
            }
        })
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )
}
