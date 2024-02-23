package com.template2024.data.sources.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExampleEntity(
    @PrimaryKey val key: Int = 1,
    val name: String
)
