package com.arwan.arwansa_scanmecalculator.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arwan.arwansa_scanmecalculator.ui.screen.main.component.ExpressionsList
import com.arwan.arwansa_scanmecalculator.ui.screen.main.component.ImagePicker
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity
import com.arwan.arwansa_scanmecalculator.ui.screen.main.component.StorageChooser
import com.arwan.arwansa_scanmecalculator.ui.screen.main.component.StorageType
import com.arwan.arwansa_scanmecalculator.ui.theme.ArwanSAScanMeCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArwanSAScanMeCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var selectedOption by remember {
        mutableStateOf(StorageType.DATABASE)
    }

    val expressions = viewModel.getExpressions(selectedOption).collectAsState(initial = emptyList())

    Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        StorageChooser {
            selectedOption = it
        }
        ImagePicker(Modifier.fillMaxWidth()) { input, result ->
            val id = System.currentTimeMillis().toString()
            val expression = ExpressionEntity(id, input, result)

            when (selectedOption) {
                StorageType.DATABASE -> viewModel.addExpressionToRoom(expression)
                StorageType.FILE -> viewModel.saveExpressionsToFileStorage(
                    expressions.value.plus(expression)
                )
            }
        }
        ExpressionsList(expressions)
    }
}

