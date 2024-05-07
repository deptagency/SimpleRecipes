package com.nomaan.simplerecipes.data.repositories

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val COMPOSE_RECIPES_PREFERENCES = "compose_recipes_preferences"
private const val SHOW_MEALS_OVERLAY_KEY = "show_meals_overlay_key"

/**
 * Class that handles saving and retrieving app preferences
 */
class DataStoreRepository private constructor(context: Context) {

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(COMPOSE_RECIPES_PREFERENCES, Context.MODE_PRIVATE)

    // Keep the overlay as a stream of changes
    private val _showMealsInformationOverlayFlow = MutableStateFlow(showMealsInformationOverlay)
    val showMealsInformationOverlayFlow: StateFlow<Boolean> = _showMealsInformationOverlayFlow

    /**
     * Get the value to show, true by default to show on first run
     */
    private val showMealsInformationOverlay: Boolean
        get() {
            return sharedPreferences.getBoolean(SHOW_MEALS_OVERLAY_KEY, true)
        }

    /**
     * Updated value will always be false, as user clicked 'Dismiss'
     */
    suspend fun setShowMealsInformationOverlayValueFalse() {
        sharedPreferences.edit {
            putBoolean(SHOW_MEALS_OVERLAY_KEY, false)
        }
        _showMealsInformationOverlayFlow.emit(showMealsInformationOverlay)
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStoreRepository? = null

        fun getInstance(context: Context): DataStoreRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = DataStoreRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}