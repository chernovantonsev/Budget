package ru.antonc.budget.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.data.entities.DemoData
import ru.antonc.budget.util.DEMO_DATA_FILENAME

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            with(AppDatabase.getInstance(applicationContext)) {

                applicationContext.assets.open(DEMO_DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->

                        val demoData: DemoData =
                            Gson().fromJson(
                                jsonReader,
                                object : TypeToken<DemoData>() {}.type
                            )

                        categoryDAO().insertAll(demoData.categories)
                        accountDAO().insertAll(demoData.accounts)
                        transactionDAO().insertAll(demoData.transactions)

                        Result.success()
                    }
                }
            }


        } catch (ex: Exception) {
            Log.e(this.javaClass.name, "Error seeding database", ex)
            Result.failure()
        }
    }
}