package com.example.provamendes.presenter.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.provamendes.R
import com.example.provamendes.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(this::transition,3000)
        auth = Firebase.auth
    }

    private fun transition() {

        val currentUser = auth.currentUser

        if (currentUser != null) {
            reloud()
        }else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    private fun reloud() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}