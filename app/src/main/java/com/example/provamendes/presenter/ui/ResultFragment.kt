package com.example.provamendes.presenter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.provamendes.R
import com.example.provamendes.data.adappter.AlunoAdapter
import com.example.provamendes.data.adappter.ResultAdapter
import com.example.provamendes.databinding.FragmentResultBinding
import com.example.provamendes.presenter.viewmodel.SharedViewModel

class ResultFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        setupList()
    }

    private fun setupList() {
        viewModel.itemList.observe(viewLifecycleOwner) {listaAlunos ->
            val aprovados = listaAlunos.filter { it.result == "Aprovado" }
            val reprovados = listaAlunos.filter { it.result == "Reprovado" }

            val adapterAprovados = ResultAdapter(requireContext(),aprovados)
            binding.listVAprovados.adapter = adapterAprovados

            val adapterReprovados = ResultAdapter(requireContext(), reprovados)
            binding.listVReprovados.adapter = adapterReprovados
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}