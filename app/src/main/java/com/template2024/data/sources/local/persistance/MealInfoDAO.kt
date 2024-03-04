package com.template2024.data.sources.local.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.template2024.data.sources.local.model.MealInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealInfoDAO {

    @Query("SELECT * FROM mealInfo")
    fun getAllSavedMeals(): Flow<List<MealInfoEntity>>

    @Query("SELECT * FROM mealInfo WHERE mealId == :mealId ORDER BY mealId ASC LIMIT 1")
    fun getMealInfo(mealId: String): MealInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMealInfo(mealInfo: MealInfoEntity)

    @Query("DELETE FROM mealInfo WHERE mealId == :mealId")
    fun deleteMealInfo(mealId: String): Int
}