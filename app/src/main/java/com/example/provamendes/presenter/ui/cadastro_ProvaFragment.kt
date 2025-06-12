package com.example.provamendes.presenter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.provamendes.R
import com.example.provamendes.data.model.Aluno
import com.example.provamendes.databinding.FragmentCadastroProvaBinding
import com.example.provamendes.domain.AlunosResponse
import com.example.provamendes.domain.ListAlunos
import com.example.provamendes.presenter.viewmodel.SharedViewModel

class cadastro_ProvaFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel

    private var _binding : FragmentCadastroProvaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroProvaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initClicks()
        formatDateInput()

    }
    private fun initClicks() {
        binding.btnCadastrar.setOnClickListener {
            val name = binding.edtNome.text.toString()
            val data = binding.edtData.text.toString()


            if (name.isNotEmpty() && data.isNotEmpty()) {
                Toast.makeText(requireContext(), "Aluno Cadastrado!", Toast.LENGTH_SHORT).show()
                val aluno = Aluno(name, data)
                viewModel.addItem(aluno)

                clear()
            }else {
                Toast.makeText(requireContext(), "Preencha os Dados", Toast.LENGTH_SHORT).show()
            }


        }
    }
    private fun clear() {

        binding.edtNome.text.clear()
    }

    private fun formatDateInput() {
        binding.edtData.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    val text = it.toString().filter { it.isDigit() } // Mantém apenas números
                    val formattedText = when {
                        text.length > 10 -> text.substring(0, 10) // Limita a 8 caracteres
                        text.length > 8 -> "${text.substring(0,2)}/${text.substring(2,4)}/${text.substring(4,6)}${text.substring(6,8)}"
                        text.length > 6 -> "${text.substring(0,2)}/${text.substring(2,4)}/${text.substring(4,6)}${text.substring(6)}"
                        text.length > 4 -> "${text.substring(0,2)}/${text.substring(2,4)}/${text.substring(4)}"
                        text.length > 2 -> "${text.substring(0,2)}/${text.substring(2)}"
                        else -> text
                    }

                    if (formattedText != s.toString()) {
                        binding.edtData.removeTextChangedListener(this) // Evita loop infinito
                        binding.edtData.setText(formattedText)
                        binding.edtData.setSelection(formattedText.length) // Mantém o cursor no final
                        binding.edtData.addTextChangedListener(this) // Re-adiciona o listener
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}

