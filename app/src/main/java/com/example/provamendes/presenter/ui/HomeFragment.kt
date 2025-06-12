package com.example.provamendes.presenter.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.provamendes.R
import com.example.provamendes.databinding.FragmentHomeBinding
import com.example.provamendes.presenter.ui.auth.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sin

class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cadastro_ProvaFragment)
        }
        binding.btnInstrutor.setOnClickListener {
        }
        binding.btnList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listAlunosragment)
        }
        binding.btnResult.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_resultFragment)
        }
        binding.imgBackHome.setOnClickListener { singOut() }
    }

    private fun singOut() {
        Firebase.auth.signOut()
        requireActivity().finish()
    }



}