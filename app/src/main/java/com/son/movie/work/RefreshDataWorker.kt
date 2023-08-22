package com.son.movie.work

//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.son.movie.database.getDatabase
//import com.son.movie.repository.MovieRepository
//import retrofit2.HttpException
//
//class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
//    CoroutineWorker(appContext, params) {
//
//    companion object {
//        const val WORK_NAME = "RefreshDataWorker"
//    }
//
//    override suspend fun doWork(): Result {
//        val database = getDatabase(applicationContext)
//        val repository = MovieRepository(database)
//
//        return try {
//            repository.refreshMovies()
//            Result.success()
//        } catch (e: HttpException) {
//            Result.retry()
//        }
//    }
//}