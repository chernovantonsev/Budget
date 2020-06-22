package ru.antonc.budget.util.extenstions

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }
