package com.nomaan.simplerecipes.data.sources.local.persistance.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson

class IngredientsConverter {
    @TypeConverter
    fun listToJson(value: List<Pair<String, String>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Pair<String, String>> {
        val list = Gson().fromJson(value, Array<PairWrapper>::class.java).toList()

        return list.toStandardPairList()
    }
}

fun List<PairWrapper>.toStandardPairList(): List<Pair<String, String>> {
    val pairsList: ArrayList<Pair<String, String>> = arrayListOf()
    for (pairWrapper in this.listIterator()) {
        pairsList.add(Pair(pairWrapper.first, pairWrapper.second))
    }
    return pairsList
}