package com.arwan.arwansa_scanmecalculator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpressionsDao {

    @Query("select * from expressions")
    fun getExpressions(): Flow<List<ExpressionEntity>>

    @Insert(onConflict = IGNORE)
    suspend fun addExpression(expressionEntity: ExpressionEntity)

}