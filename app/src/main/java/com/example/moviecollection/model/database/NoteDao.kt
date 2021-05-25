package com.example.moviecollection.model.database

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity WHERE id LIKE :id")
    fun getDataById(id: Long?): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)
}