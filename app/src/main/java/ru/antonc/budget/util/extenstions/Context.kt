package ru.antonc.budget.util.extenstions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun Context.getAttributeColor(attributeId: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attributeId, typedValue, true)
    val colorRes = typedValue.resourceId
    var color = -1
    try {
        color = ContextCompat.getColor(this, colorRes)
    } catch (ignored: Resources.NotFoundException) {
    }
    return color
}

fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}