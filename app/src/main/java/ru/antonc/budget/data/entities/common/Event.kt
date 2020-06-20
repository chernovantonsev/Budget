package ru.antonc.budget.data.entities.common

class Event : EventContent<Boolean>(true) {

    fun getEventIfNotHandled(): Boolean? {
        return getContentIfNotHandled()
    }
}