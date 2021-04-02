package com.example.diary.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.diary.R
import com.example.diary.databinding.FragmentStatisticsBinding
import com.example.diary.dateFormat
import com.example.diary.model.database.AppDataBase
import com.example.diary.model.entity.Measurement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    lateinit var binding: FragmentStatisticsBinding

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this, StatisticsViewModelFactory(
                AppDataBase.getInstance(requireContext()).getMeasurementDao()
            )
        ).get(StatisticsViewModel::class.java)

        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastWeekMeasurements.observe(viewLifecycleOwner, {
            binding.lastWeekPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusWeeks(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgWeekWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgWeekBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastMonthMeasurements.observe(viewLifecycleOwner, {
            binding.lastMonthPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusMonths(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgMonthWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgMonthBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastQuarterMeasurements.observe(viewLifecycleOwner, {
            binding.lastQuarterPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusMonths(3).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgQuarterWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgQuarterBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastYearMeasurements.observe(viewLifecycleOwner, {
            binding.lastYearPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusYears(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgYearWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgYearBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })
    }

    private fun calculateAvgWeight(measurements: List<Measurement>) =
        if (measurements.isNotEmpty()) {
            var sumWeight = 0F
            measurements.forEach { sumWeight += it.weight }
            sumWeight / measurements.size
        } else {
            0F
        }

    private fun calculateAvgBodyIndex(measurements: List<Measurement>) =
        if (measurements.isNotEmpty()) {
            var sumBodyIndex = 0F
            measurements.forEach { sumBodyIndex += it.indexWeight }
            sumBodyIndex / measurements.size
        } else {
            0F
        }
}
