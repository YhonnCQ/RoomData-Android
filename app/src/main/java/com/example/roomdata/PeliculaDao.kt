package com.example.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PeliculaDao {

    @Query("SELECT * FROM peliculas")
    suspend fun getPeliculas(): List<Pelicula>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPelicula(pelicula: Pelicula)

    @Query("DELETE FROM peliculas")
    suspend fun deleteAll()

    @Query("SELECT * FROM peliculas WHERE title like :title")
    suspend fun getByTitle(title: String): List<Pelicula>
}