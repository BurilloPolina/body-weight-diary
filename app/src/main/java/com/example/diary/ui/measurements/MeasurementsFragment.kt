package com.example.diary.ui.measurements

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.R
import com.example.diary.databinding.DialogAddMeasurementBinding
import com.example.diary.databinding.FragmentMeasurementsBinding
import com.example.diary.model.database.AppDataBase
import com.example.diary.model.entity.Measurement
import com.example.diary.model.prefs.Prefs
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.lang.NumberFormatException

class MeasurementsFragment : Fragment(R.layout.fragment_measurements) {

    lateinit var binding: FragmentMeasurementsBinding

    private lateinit var viewModel: MeasurementsViewModel

    private var measurementsAdapter: MeasurementsAdapter? = null

    private var measurementDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this, MeasurementsViewModelFactory(
                AppDataBase.getInstance(requireContext()).getMeasurementDao(),
                createPrefs()
            )
        ).get(MeasurementsViewModel::class.java)

        binding = FragmentMeasurementsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        measurementsAdapter = MeasurementsAdapter(
            viewModel::deleteMeasurement,
            this::editMeasurement
        )

        binding.measurements.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = measurementsAdapter
        }

        viewModel.measurements.observe(viewLifecycleOwner, { measurement ->
            measurementsAdapter?.setMeasurements(measurement)
        })

        binding.addMeasurement.setOnClickListener {
            createMeasurementDialog(null)
            measurementDialog?.show()
        }
    }

    private fun editMeasurement(measurement: Measurement) {
        createMeasurementDialog(measurement)
        measurementDialog?.show()
    }

    private fun createMeasurementDialog(currentMeasurement: Measurement?) {
        val dialogView = DialogAddMeasurementBinding.inflate(layoutInflater)
        currentMeasurement?.let {
            dialogView.weightEditText.append(String.format("%.1f", it.weight))
            dialogView.weightTitle.text = getString(R.string.edit_measurement)
        }

        measurementDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                val weight = try {
                    dialogView.weightEditText.text.toString().toFloat()
                } catch (exception: NumberFormatException) {
                    -1F
                }

                when {
                    weight <= 0F -> toast(getString(R.string.form_weight_error))
                    currentMeasurement != null -> viewModel.editMeasurement(currentMeasurement, weight)
                    else -> viewModel.addMeasurement(weight)
                }
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, _ ->
                dialog.cancel()
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )

    private fun toast(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}
