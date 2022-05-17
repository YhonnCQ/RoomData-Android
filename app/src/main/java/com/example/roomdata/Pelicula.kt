package com.example.roomdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peliculas")
data class Pelicula(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val titulo: String,
    @ColumnInfo(name = "classification") val clasificacion: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String
)