package ru.antonc.budget.util.extenstions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }

fun <T1, T2, R> LiveData<T1>.combineWith(
    liveData: LiveData<T2>,
    block: (T1?, T2?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block.invoke(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block.invoke(this.value, liveData.value)
    }
    return result
}


fun <T1, T2, T3, R> LiveData<T1>.combineWith(
    liveData2: LiveData<T2>,
    liveData3: LiveData<T3>,
    block: (T1?, T2?, T3?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block.invoke(this.value, liveData2.value, liveData3.value)
    }
    result.addSource(liveData2) {
        result.value = block.invoke(this.value, liveData2.value, liveData3.value)
    }
    result.addSource(liveData3) {
        result.value = block.invoke(this.value, liveData2.value, liveData3.value)
    }
    return result
}
