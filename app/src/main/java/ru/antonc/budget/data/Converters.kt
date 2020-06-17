package ru.antonc.budget.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.antonc.budget.data.entities.TransactionType

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTransactionType(transactionType: TransactionType): String {
        return Gson().toJson(transactionType)
    }

    @TypeConverter
    @JvmStatic
    fun toTransactionType(transactionType: String): TransactionType {
        return Gson().fromJson(transactionType, object : TypeToken<TransactionType>() {}.type)
    }
}