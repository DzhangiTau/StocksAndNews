package com.assessment.stocksandnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var text: TextView
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        path = application.filesDir.absolutePath + "stocks"

        text = findViewById(R.id.tv_text)
        text.setOnClickListener {
            viewModel.getNews()
            viewModel.downloadStocks()
        }

        viewModel.news.observeForever {
            val index = Random.nextInt(0, 98)
            text.text = it.articles[index].title
        }

        viewModel.stocks.observeForever {
            saveFile(it.body(), path)
        }
    }

    private fun saveFile(body: ResponseBody?, path: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(path)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return path
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }
}