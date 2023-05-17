package com.arwan.arwansa_scanmecalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "expressions")
data class ExpressionEntity(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo val id: String,

    @SerializedName("input")
    @ColumnInfo val input: String,

    @SerializedName("result")
    @ColumnInfo val result: String,
)