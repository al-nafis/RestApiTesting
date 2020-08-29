package com.mnafis.restapitesting.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_table")
    fun getMovies(): Observable<Movie>

    @Query("SELECT * FROM movies_table WHERE title LIKE :name")
    fun getMovieByName(name: String): Single<Movie>

    @Insert
    fun insertMovies(vararg movies: Movie) : Completable

    @Delete
    fun deleteMovie(movie: Movie) : Single<Int>
}