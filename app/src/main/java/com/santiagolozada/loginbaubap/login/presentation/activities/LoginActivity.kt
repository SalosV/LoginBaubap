package com.santiagolozada.loginbaubap.login.presentation.activities

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.santiagolozada.loginbaubap.R
import com.santiagolozada.loginbaubap.common.extensions.editText.afterTextChanged
import com.santiagolozada.loginbaubap.databinding.ActivityLoginBinding
import com.santiagolozada.loginbaubap.login.presentation.LoggedInUserView
import com.santiagolozada.loginbaubap.login.presentation.LoginFormState
import com.santiagolozada.loginbaubap.login.presentation.LoginResult
import com.santiagolozada.loginbaubap.login.presentation.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels()
        initListenerEditText()
        initListenerButton()
    }

    private fun initViewModels() {
        loginViewModel.loginFormState.observe(this@LoginActivity, ::handleFormState)
        loginViewModel.loginResult.observe(this@LoginActivity, ::handleResultLogin)
        loginViewModel.loadingState.observe(this@LoginActivity, ::setLoadingVisibilty)
    }

    private fun initListenerEditText() {
        binding.editTextEmail.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

        binding.editTextPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loginViewModel.login(
                            binding.editTextEmail.text.toString(),
                            binding.editTextPassword.text.toString()
                        )
                    }
                }
                false
            }
        }
    }

    private fun initListenerButton() {
        binding.buttonLogin.setOnClickListener {
            if (loginViewModel.loginFormState.value?.isDataValid == true) {
                loginViewModel.login(binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString())
            } else {
                showLoginFailed(R.string.invalid_data)
            }
        }
    }

    private fun handleFormState(loginState: LoginFormState) {
        if (loginState.usernameError != null) {
            binding.editTextEmail.error = getString(loginState.usernameError)
        }
        if (loginState.passwordError != null) {
            binding.editTextPassword.error = getString(loginState.passwordError)
        }
    }

    private fun handleResultLogin(loginResult: LoginResult) {
        if (loginResult.error != null) {
            showLoginFailed(loginResult.error)
        }
        if (loginResult.success != null) {
            updateUiWithUser(loginResult.success)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun setLoadingVisibilty(isVisible: Boolean) {
        binding.loading.isVisible = isVisible
        if (isVisible) binding.lottieLoading.playAnimation() else binding.lottieLoading.pauseAnimation()
    }

}