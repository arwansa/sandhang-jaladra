package com.arwan.arwansa_scanmecalculator.ui.screen.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class StorageType(val value: String) {
    DATABASE("Database Storage"),
    FILE("File Storage")
}

@Composable
fun StorageChooser(onClick: (selectedOption: StorageType) -> Unit) {
    var selectedOption by remember {
        mutableStateOf(StorageType.DATABASE)
    }
    Column {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == StorageType.DATABASE,
                onClick = {
                    selectedOption = StorageType.DATABASE
                    onClick.invoke(StorageType.DATABASE)
                }
            )
            Text(
                text = StorageType.DATABASE.value,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == StorageType.FILE,
                onClick = {
                    selectedOption = StorageType.FILE
                    onClick.invoke(StorageType.FILE)
                }
            )
            Text(
                text = StorageType.FILE.value,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}