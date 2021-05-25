package com.example.moviecollection.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviecollection.App

@androidx.room.Database(
    entities = [
        HistoryEntity::class,
        NoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "app_database.db"
        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }
}