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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnEntrarLogin.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.edtEmailLogin.text.toString().trim()
        val password = binding.edtPasswordLogin.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginUser(email, password)
        }else {
            Toast.makeText(requireContext(), "Preencha todos os Campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { login ->
                if (login.isSuccessful) {
                    Toast.makeText(requireContext(), "Bem-Vindo", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else {
                    Toast.makeText(requireContext(), "Não foi possivel acessar", Toast.LENGTH_SHORT).show()


                }
            }


    }


}