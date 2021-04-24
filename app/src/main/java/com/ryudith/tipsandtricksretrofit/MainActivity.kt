package com.ryudith.tipsandtricksretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ryudith.tipsandtricksretrofit.custom.Movie
import com.ryudith.tipsandtricksretrofit.databinding.ActivityMainBinding
import com.ryudith.tipsandtricksretrofit.util.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

private const val TAG = "DEBUG_DATA"
class MainActivity : AppCompatActivity() {
    private lateinit var activityRef : AppCompatActivity
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRef = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitRef = RetrofitHelper.getRetrofitRef()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
//                val resp = retrofitRef.getMethod()
//                Log.d(TAG, "resp : ${resp.body()}")

//                val respSession = retrofitRef.getMethod()
//                Log.d(TAG, "resp session : ${respSession.body()}")

//                val movieResp = retrofitRef.getMoviesResponse()
//                Log.d(TAG, "movie resp : ${movieResp.body()}")

                val movieList = retrofitRef.getMoviesList()
                Log.d(TAG, "movie list : ${movieList.body() as List<Movie>}")

            } catch (e : SocketTimeoutException) {
                Log.d(TAG, "SocketTimeoutException exception : ${e.message}")

            } catch (e : Exception) {
                Log.d(TAG, "General exception : ${e.message}")

            }
        }
    }
}