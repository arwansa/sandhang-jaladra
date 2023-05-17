package com.arwan.arwansa_scanmecalculator.ui.screen.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity

@Composable
fun ExpressionsList(expressions: State<List<ExpressionEntity>>) {
    LazyColumn {
        items(items = expressions.value) { expression ->
            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                    .fillMaxWidth(),
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = "input: ${expression.input}")
                    Text(text = "result: ${expression.result}")
                }
            }
        }
    }
}