package com.sahak7an.t3.feature.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.sahak7an.t3.core.ui.SessionManager
import com.sahak7an.t3.feature.login.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validator = EmailPasswordValidator()

        fun updateButtonState() {
            binding.buttonLogin.isEnabled = validator.isValid(
                binding.editEmail.text?.toString().orEmpty(),
                binding.editPassword.text?.toString().orEmpty()
            )
        }

        binding.editEmail.addTextChangedListener { text ->
            val filtered = text?.toString()?.filter { it.code < 128 }
            if (filtered != text?.toString()) {
                val pos = binding.editEmail.selectionStart
                binding.editEmail.setText(filtered)
                binding.editEmail.setSelection(filtered?.length ?: 0)
            }
            updateButtonState()
        }
        binding.editPassword.addTextChangedListener { updateButtonState() }
        updateButtonState()

        binding.buttonLogin.setOnClickListener {
            SessionManager.setLoggedIn(requireContext(), true)
            val request = NavDeepLinkRequest.Builder
                .fromUri(Uri.parse("t3://home"))
                .build()
            val options = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(findNavController().graph.startDestinationId, true)
                .build()
            findNavController().navigate(request, options)
        }
        binding.buttonVk.setOnClickListener {
            openUrl("https://vk.com/")
        }
        binding.buttonOk.setOnClickListener {
            openUrl("https://ok.ru/")
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class EmailPasswordValidator {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    fun isValid(email: String, password: String): Boolean {
        if (!emailRegex.matches(email)) return false
        return email.isNotBlank() && password.isNotBlank()
    }
}

