package com.prediksi.bocor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prediksi.bocor.databinding.ItemResultBinding
import com.prediksi.bocor.model.PredictionResult

class ResultAdapter(private var results: List<PredictionResult>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = results[position]
        with(holder.binding) {
            tvMethod.text = item.method
            tvNumbers.text = item.numbers.joinToString(" • ")
            tvConfidence.text = "${item.confidence}%"
            tvDescription.text = item.description
            progressConfidence.progress = item.confidence
        }
    }

    override fun getItemCount() = results.size

    fun updateData(newResults: List<PredictionResult>) {
        results = newResults
        notifyDataSetChanged()
    }
}
