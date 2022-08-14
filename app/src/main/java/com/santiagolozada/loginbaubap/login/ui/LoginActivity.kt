package com.santiagolozada.loginbaubap.login.ui

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.santiagolozada.loginbaubap.R
import com.santiagolozada.loginbaubap.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login?.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.editTextEmail?.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.editTextPassword?.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            setLoadingVisibilty(false)
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        binding.editTextEmail?.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.editTextEmail?.text.toString(),
                binding.editTextPassword?.text.toString()
            )
        }

        binding.editTextPassword?.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.editTextEmail?.text.toString(),
                    binding.editTextPassword?.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loginViewModel.login(
                            binding.editTextEmail?.text.toString(),
                            binding.editTextPassword?.text.toString()
                        )
                        setLoadingVisibilty(true)
                    }
                }
                false
            }

            binding.buttonLogin?.setOnClickListener {
                setLoadingVisibilty(true)
                loginViewModel.login(binding.editTextEmail?.text.toString(),
                    binding.editTextPassword?.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
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
        if (isVisible) binding.lottieLoading?.playAnimation() else binding.lottieLoading?.pauseAnimation()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}