package com.example.provamendes.presenter.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.provamendes.R
import com.example.provamendes.databinding.FragmentLoginBinding
import com.example.provamendes.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClicks()
    }
    private fun initClicks() {
        binding.btnCadastrarRegister.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.txtEmail.text.toString().trim()
        val password = binding.txtPpassword.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            registerUser(email, password)
        }else {
            Toast.makeText(requireContext(), "Preencha todos os Campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)

                } else {
                    Toast.makeText(requireContext(), "Falha no Registro", Toast.LENGTH_SHORT).show()


                }
            }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}