package com.portal.weatherapp.ui.login.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentRegisterBinding
import com.portal.weatherapp.ui.login.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by activityViewModels()
    private val binding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)

    override fun observeVariables() {

    }

    override fun initUI(savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setButtonOnClick {
            register()
        }
    }

    private fun register() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etPasswordConfirm.text.toString()

        if (email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
            if (password == confirmPassword) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Registration failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }}