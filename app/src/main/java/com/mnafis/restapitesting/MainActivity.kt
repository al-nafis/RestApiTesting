package com.mnafis.restapitesting

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.mnafis.restapitesting.databinding.ActivityMainBinding
import com.mnafis.restapitesting.omdb.MovieResponse
import com.mnafis.restapitesting.omdb.OmdbManager
import com.mnafis.restapitesting.rest_countries.CountryManager
import com.mnafis.restapitesting.rest_countries.CountryResponse
import com.mnafis.restapitesting.room_database.Movie
import com.mnafis.restapitesting.room_database.MovieDao
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private lateinit var moviesTable: MovieDao

    private val countryManager = CountryManager()
    private val omdbManager = OmdbManager()

    val movieTitle = ObservableField("")
    val year = ObservableField("")
    val rating = ObservableField("")
    val poster = ObservableField("")
    val language = ObservableField("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        executeMovieCallCoroutine()
        executeMovieCallRx()
        executeMovieCallWithNoAdapter()
    }

    private fun executeMovieCallCoroutine() {
        GlobalScope.launch {
            delay(3000L)
            try {
                val response = omdbManager.getMovieDetailsCoroutine("Avengers")

                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    Log.d("TESTING-DATABASE", "COROUTINE: ${data.movieTitle}")
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occurred: ${response.message()}",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun executeMovieCallWithNoAdapter() {
        countryManager.getCountryByName("usa").enqueue(object : Callback<List<CountryResponse>> {
            override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                Log.d("TESTING-DATABASE", t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CountryResponse>>,
                response: Response<List<CountryResponse>>
            ) {
                Log.d("TESTING-DATABASE", response.body()!!.first().name)
            }
        })
    }

    private fun executeMovieCallRx() {
        moviesTable = RoomDatabaseBuilder.getDatabase()!!.moviesDao()

        disposable.add(
            omdbManager.getMovieDetailsRx("something")
                .subscribe(
                    {
                        onSuccess(it)
                    },
                    {
                        onError(it)
                    }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    fun saveMovie() {
        val longs = System.currentTimeMillis()
        Log.d("TESTING-DATABASE", "long: $longs")
        Log.d("TESTING-DATABASE", "int: " + longs.toInt())
        val movie = Movie(longs.toString(), movieTitle.get()!!, year.get()!!, poster.get()!!)
//        Observable.just(movie).subscribeOn(Schedulers.io())
//            .subscribe({ moviesTable.insertMovies(it) }, { it.printStackTrace() })

        disposable.add(
            moviesTable.insertMovies(movie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("TESTING-DATABASE", "Data saved")
                        getData()
                    },
                    { Log.d("TESTING-DATABASE", "Data not Saved: " + it.message) })
        )
    }

    private fun getData() {
        disposable.add(
            moviesTable.getMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { printMovie(it) },
                    { Log.d("TESTING-DATABASE", "Data not found: " + it.message) })
        )
    }

    private fun printMovie(movie: Movie) {
        Log.d("TESTING-DATABASE", "Data Found: " + movie.uid + " " + movie.title)
    }

    private fun onSuccess(movieResponse: MovieResponse) {
        movieTitle.set(movieResponse.movieTitle)
        year.set(movieResponse.year)
//        rating.set(movieResponse.imdbRating)
        poster.set(movieResponse.poster)
//        language.set(movieResponse.language)

//        Log.d("TESTING-DATABASE", movieResponse.movieList[0].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[1].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[2].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[3].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[4].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[5].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[6].movieTitle)
//        Log.d("TESTING-DATABASE", movieResponse.movieList[7].movieTitle)
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        Log.d("TESTING-DATABASE", throwable.message)
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImage(view: ImageView, path: String) {
            try {
                Picasso.get().load(path).into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
