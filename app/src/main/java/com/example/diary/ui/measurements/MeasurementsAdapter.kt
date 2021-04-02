package com.example.diary.ui.measurements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R
import com.example.diary.databinding.ItemMeasurementBinding
import com.example.diary.dateTimeFormat
import com.example.diary.model.entity.Measurement

class MeasurementsAdapter(
    private val deleteOnClickListener: (Measurement) -> Unit,
    private val editOnClickListener: (Measurement) -> Unit
) : RecyclerView.Adapter<MeasurementsAdapter.MeasurementHolder>() {

    private val measurements = mutableListOf<Measurement>()

    fun setMeasurements(newMeasurements: List<Measurement>) {
        measurements.apply {
            clear()
            addAll(newMeasurements)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementHolder {
        val binding = ItemMeasurementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeasurementHolder(binding)
    }

    override fun onBindViewHolder(holder: MeasurementHolder, position: Int) =
        holder.bind(measurements[position])

    override fun getItemCount() = measurements.size

    inner class MeasurementHolder(private val binding: ItemMeasurementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(measurement: Measurement) {
            binding.weight.text =
                itemView.context.getString(R.string.item_weight_value, measurement.weight)
            binding.bodyIndex.text = String.format("%.2f", measurement.indexWeight)
            binding.date.text = measurement.dateOfMeasurement.dateTimeFormat()
            binding.difference.text =
                itemView.context.getString(R.string.item_weight_value, measurement.difference)

            binding.editButton.setOnClickListener { editOnClickListener(measurement) }

            binding.deleteButton.setOnClickListener { deleteOnClickListener(measurement) }
        }
    }
}