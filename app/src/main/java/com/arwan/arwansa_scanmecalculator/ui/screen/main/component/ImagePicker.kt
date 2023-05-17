package com.arwan.arwansa_scanmecalculator.ui.screen.main.component

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.arwan.arwansa_scanmecalculator.BuildConfig
import com.arwan.arwansa_scanmecalculator.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import net.objecthunter.exp4j.ExpressionBuilder
import java.io.File
import java.io.IOException

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory,
            )
            val authority = "${context.packageName}.fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }

        fun recognizeImage(
            context: Context,
            imageUri: Uri,
            callback: (input: String, result: String) -> Unit
        ) {
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            var image: InputImage? = null
            try {
                image = InputImage.fromFilePath(context, imageUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            image?.let {
                recognizer.process(it)
                    .addOnSuccessListener { visionText ->
                        calculateResult(visionText.text)?.let { result ->
                            callback.invoke(visionText.text, result.toString())
                        }
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        private fun calculateResult(expression: String): Double? {
            try {
                if (!expression.matches(Regex("[0-9+\\-*/ ]+"))) return null
                val exp = ExpressionBuilder(expression.replace(" ", "")).build()
                return exp.evaluate()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }
}

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    callback: (input: String, result: String) -> Unit
) {
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            imageUri?.let {
                ComposeFileProvider.recognizeImage(context, it, callback)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success) {
                imageUri?.let {
                    ComposeFileProvider.recognizeImage(context, it, callback)
                }
            }
        }
    )

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (BuildConfig.HAS_BUILT_IN_CAMERA) {
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        val uri = ComposeFileProvider.getImageUri(context)
                        imageUri = uri
                        cameraLauncher.launch(uri)
                    },
                ) {
                    Text(
                        text = "Take photo"
                    )
                }
            } else {
                Button(
                    onClick = {
                        imagePicker.launch("image/*")
                    },
                ) {
                    Text(
                        text = "Select Image"
                    )
                }
            }
        }
    }
}
