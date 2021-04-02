package com.example.diary.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.diary.R
import com.example.diary.databinding.FragmentProfileBinding
import com.example.diary.model.database.AppDataBase
import com.example.diary.model.prefs.Prefs
import com.google.gson.Gson

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(
                AppDataBase.getInstance(requireContext()).getMeasurementDao(),
                createPrefs()
            )
        ).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastMeasurement.observe(viewLifecycleOwner, {
            binding.name.text = viewModel.user?.name
            binding.userHeight.text = getString(R.string.profile_user_height, viewModel.user?.height)

            if (it != null) {
                binding.userWeight.text = getString(R.string.profile_user_weight, it.weight)
                binding.userBodyIndex.text = getString(R.string.profile_body_index, it.indexWeight)
            } else {
                binding.userWeight.visibility = View.GONE
                binding.userBodyIndex.visibility = View.GONE
            }

        })
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )
}