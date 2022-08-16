package com.santiagolozada.loginbaubap.common.viewModel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.santiagolozada.loginbaubap.common.coroutines.Scope
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopedViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Implementation(uiDispatcher) {

    init {
        this.initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}