package com.arwan.arwansa_scanmecalculator.data.repository

import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity
import com.arwan.arwansa_scanmecalculator.data.local.ExpressionsDao
import com.arwan.arwansa_scanmecalculator.data.local.FileStorage
import kotlinx.coroutines.flow.Flow

typealias Expressions = List<ExpressionEntity>

interface ExpressionsRepository {
    fun getExpressionsFromRoom(): Flow<Expressions>
    suspend fun addExpressionToRoom(expressionEntity: ExpressionEntity)
    suspend fun getExpressionsFromFileStorage(): Expressions
    suspend fun saveExpressionsToFileStorage(expressions: Expressions)
}

class ExpressionsRepositoryImpl(
    private val expressionsDao: ExpressionsDao,
    private val fileStorage: FileStorage
) : ExpressionsRepository {

    override fun getExpressionsFromRoom() = expressionsDao.getExpressions()

    override suspend fun addExpressionToRoom(expressionEntity: ExpressionEntity) =
        expressionsDao.addExpression(expressionEntity)

    override suspend fun getExpressionsFromFileStorage(): Expressions {
        return fileStorage.loadData().orEmpty()
    }

    override suspend fun saveExpressionsToFileStorage(expressions: Expressions) {
        fileStorage.run {
            deleteFile()
            saveData(expressions)
        }
    }
}