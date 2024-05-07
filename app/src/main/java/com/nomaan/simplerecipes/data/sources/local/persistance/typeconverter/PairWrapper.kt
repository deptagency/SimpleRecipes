package com.nomaan.simplerecipes.data.sources.local.persistance.typeconverter

// Class to help Room with Pair<T, T> operations
data class PairWrapper(
    val first: String,
    val second: String
)