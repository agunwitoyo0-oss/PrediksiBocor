package com.prediksi.bocor

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prediksi.bocor.adapter.ResultAdapter
import com.prediksi.bocor.ai.AIPatternLearner
import com.prediksi.bocor.databinding.ActivityMainBinding
import com.prediksi.bocor.methods.Indeks
import com.prediksi.bocor.methods.MistikBaru
import com.prediksi.bocor.methods.MistikLama
import com.prediksi.bocor.methods.TaysenShio
import com.prediksi.bocor.model.PredictionResult

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ResultAdapter
    private val aiLearner = AIPatternLearner()
    private val resultList = mutableListOf<PredictionResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = ResultAdapter(resultList)
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        binding.rvResults.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnPredict.setOnClickListener {
            val input = binding.etInput.text.toString().trim()
            if (input.isEmpty() || input.length < 2) {
                binding.etInput.error = "Masukkan minimal 2 digit angka"
                return@setOnClickListener
            }
            predictAll(input)
        }

        binding.btnClear.setOnClickListener {
            binding.etInput.text?.clear()
            resultList.clear()
            adapter.updateData(resultList)
            binding.tvAiStatus.text = "AI Status: Siap menganalisis"
        }
    }

    private fun predictAll(input: String) {
        val results = mutableListOf<PredictionResult>()

        results.add(MistikLama.predict(input))
        results.add(MistikBaru.predict(input))
        results.add(Indeks.predict(input))
        results.add(TaysenShio.predict(input))

        aiLearner.addHistory(input)
        val aiResult = aiLearner.analyze(results)
        results.add(0, aiResult)

        resultList.clear()
        resultList.addAll(results)
        adapter.updateData(resultList)

        binding.tvAiStatus.text = "🤖 AI telah mempelajari ${aiLearner.getHistorySize()} pola input"

        val anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.rvResults.startAnimation(anim)
    }
}
