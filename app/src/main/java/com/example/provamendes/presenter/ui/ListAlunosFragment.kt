package com.example.provamendes.presenter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.provamendes.data.adappter.AlunoAdapter
import com.example.provamendes.data.model.Aluno
import com.example.provamendes.databinding.FragmentListAlunosBinding
import com.example.provamendes.presenter.viewmodel.SharedViewModel

class ListAlunosFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel

    private var _binding : FragmentListAlunosBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListAlunosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]



        listAlunos()
    }

    private fun listAlunos() {
        viewModel.itemList.observe(viewLifecycleOwner) { listaAlunos ->
            val listView = binding.listVListAlunos
            val adapter = AlunoAdapter(
                requireContext(), listaAlunos,
                onResultUpdate = {
                viewModel.updateList(listaAlunos)
                },
                showOnlyPending = true
            )
            listView.adapter = adapter
        }
    }

}