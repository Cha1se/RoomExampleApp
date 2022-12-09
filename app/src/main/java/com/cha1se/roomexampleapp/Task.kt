package com.cha1se.roomexampleapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.properties.Delegates


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val task: String
)