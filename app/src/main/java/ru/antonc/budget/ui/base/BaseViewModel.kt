package ru.antonc.budget.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val dataCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        dataCompositeDisposable.clear()
    }
}