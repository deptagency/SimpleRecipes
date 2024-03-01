package com.template2024.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.template2024.data.sources.local.model.MealInfoEntity
import com.template2024.data.sources.local.persistance.MealInfoDAO
import com.template2024.data.sources.local.persistance.typeconverter.IngredientsConverter

@Database(entities = [MealInfoEntity::class], version = 1)
@TypeConverters(IngredientsConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun mealInfoDAO(): MealInfoDAO
}