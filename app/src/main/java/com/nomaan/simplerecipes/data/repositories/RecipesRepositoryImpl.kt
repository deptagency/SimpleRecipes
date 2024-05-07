package com.nomaan.simplerecipes.data.repositories

import com.nomaan.simplerecipes.data.sources.local.Database
import com.nomaan.simplerecipes.data.sources.local.model.toMealInfo
import com.nomaan.simplerecipes.data.sources.local.model.toMealInfoEntity
import com.nomaan.simplerecipes.data.sources.remote.api.API
import com.nomaan.simplerecipes.data.sources.remote.dto.response.toCategory
import com.nomaan.simplerecipes.data.sources.remote.dto.response.toMeal
import com.nomaan.simplerecipes.data.sources.remote.dto.response.toMealInfo
import com.nomaan.simplerecipes.domain.models.Category
import com.nomaan.simplerecipes.domain.models.Meal
import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.domain.repositories.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipesRepositoryImpl(
    private val api: API,
    private val database: Database
) : RecipesRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val categories = api.getCategories().categories
                .map { it.toCategory() }

            Result.success(categories)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getMealsByCategory(category: String): Result<List<Meal>> {
        return try {
            val categoryMealsResponse = api.getCategoryMealsList(category)

            Result.success(
                categoryMealsResponse.mealsList.map {
                    it.toMeal()
                }
            )
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getMealDetails(mealId: String): Result<MealInfo> {
        // Look for meal in database
        var savedMealInfo = database.mealInfoDAO().getMealInfo(mealId)?.toMealInfo()

        return try {
            val mealDetailsResponse = api.getMealDetails(mealId)

            // If meal is saved in database, update it and return updated meal from database
            if (savedMealInfo != null) {
                database.mealInfoDAO()
                    .saveMealInfo(mealDetailsResponse.meals[0].toMealInfo().toMealInfoEntity())

                savedMealInfo = database.mealInfoDAO().getMealInfo(mealId)?.toMealInfo()

                Result.success(
                    savedMealInfo!!
                )
            } else {
                Result.success(
                    mealDetailsResponse.meals[0].toMealInfo()
                )
            }
        } catch (ex: Exception) {
            if (savedMealInfo != null) {
                Result.success(savedMealInfo)
            } else {
                Result.failure(ex)
            }
        }
    }

    override suspend fun getAllSavedMeals(): Flow<List<MealInfo>> {
        // Look for meals in database
        return database.mealInfoDAO().getAllSavedMeals().map {
            it.map { mealInfoEntity -> mealInfoEntity.toMealInfo() }
        }
    }

    override suspend fun saveMealDetails(mealInfo: MealInfo): Result<Boolean> {
        database.mealInfoDAO()
            .saveMealInfo(
                mealInfo.toMealInfoEntity()
            )

        return Result.success(true)
    }

    override suspend fun deleteMealDetails(mealId: String): Result<Boolean> {
        val deleted = database.mealInfoDAO().deleteMealInfo(mealId) > 0

        return Result.success(deleted)
    }
}