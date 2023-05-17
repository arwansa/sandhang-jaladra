package com.arwan.arwansa_scanmecalculator.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity
import com.arwan.arwansa_scanmecalculator.data.repository.ExpressionsRepository
import com.arwan.arwansa_scanmecalculator.ui.screen.main.component.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ExpressionsRepository
) : ViewModel() {

    private val expressionsFlow = MutableStateFlow<List<ExpressionEntity>>(emptyList())

    fun getExpressions(storageType: StorageType): Flow<List<ExpressionEntity>> {
        return when (storageType) {
            StorageType.DATABASE -> repo.getExpressionsFromRoom()
            StorageType.FILE -> expressionsFlow.also { getExpressionsFromFileStorage() }
        }
    }

    fun addExpressionToRoom(expressionEntity: ExpressionEntity) = viewModelScope.launch {
        repo.addExpressionToRoom(expressionEntity)
    }

    fun getExpressionsFromFileStorage() = viewModelScope.launch {
        expressionsFlow.value = repo.getExpressionsFromFileStorage()
    }

    fun saveExpressionsToFileStorage(expressions: List<ExpressionEntity>) =
        viewModelScope.launch {
            repo.saveExpressionsToFileStorage(expressions)
            getExpressionsFromFileStorage()
        }
}