package com.arwan.arwansa_scanmecalculator.data.local

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.arwan.arwansa_scanmecalculator.data.entity.ExpressionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileStorage(private val context: Context) {

    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private var encryptedFile: EncryptedFile? = null

    private fun getEncryptedFile(): EncryptedFile? {
        try {
            if (encryptedFile == null || !file.exists()) {
                encryptedFile = EncryptedFile.Builder(
                    context,
                    file,
                    masterKey,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
                ).build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encryptedFile
    }

    private val file: File by lazy {
        File(context.filesDir, FILENAME)
    }

    suspend fun saveData(data: List<ExpressionEntity>) = withContext(Dispatchers.IO) {
        try {
            val json = Gson().toJson(data)
            getEncryptedFile()?.openFileOutput()?.use { outputStream ->
                outputStream.write(json.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun loadData(): List<ExpressionEntity>? = withContext(Dispatchers.IO) {
        return@withContext try {
            getEncryptedFile()?.openFileInput()?.use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                val listType = object : TypeToken<List<ExpressionEntity>>() {}.type
                Gson().fromJson<List<ExpressionEntity>>(json, listType)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteFile() = withContext(Dispatchers.IO) {
        try {
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val FILENAME = "expressions.json"
    }
}