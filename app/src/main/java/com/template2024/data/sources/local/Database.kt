package com.template2024.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template2024.data.sources.local.model.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1)
abstract class Database : RoomDatabase() {
}