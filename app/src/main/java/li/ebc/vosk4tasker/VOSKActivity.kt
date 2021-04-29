package li.ebc.vosk4tasker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import li.ebc.vosk4tasker.databinding.ActivityVoskBinding
import li.ebc.vosk4tasker.tasker.SpokenEvent
import li.ebc.vosk4tasker.tasker.SpokenEventOutput
import org.json.JSONObject
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.io.IOException
import java.lang.Exception


class VOSKActivity : AppCompatActivity(), RecognitionListener {
    lateinit var view: ActivityVoskBinding
    lateinit var prompt: String
    lateinit var eventId: String
    lateinit var model: Model

    var speechService: SpeechService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityVoskBinding.inflate(layoutInflater)
        prompt = intent.getStringExtra("prompt") ?: "Speak Now..."
        eventId = intent.getStringExtra("event_id") ?: ""

        setContentView(view.root)

        LibVosk.setLogLevel(LogLevel.INFO)

        val permissionCheck =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        } else {
            initModel()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Recognizer initialization is a time-consuming and it involves IO,
                // so we execute it in async task
                initModel()
            } else {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        speechService?.stop()
        speechService?.shutdown()
    }

    private fun initModel() {
        Log.i("VOSK4T", "Unpacking model...")
        StorageService.unpack(this, "model", "m",
            { m ->
                model = m
                view.statusText.text = prompt

                val rec = Recognizer(m, 16000.0f)
                speechService = SpeechService(rec, 16000.0f)
                speechService!!.startListening(this)
                Log.i("VOSK4T", "Listening!")
            }
        ) { exception: IOException ->
            view.statusText.text = "Exception unpacking model: " + exception.message
        }
    }

    override fun onPartialResult(result: String) {
        val obj = JSONObject(result)
        val text = obj.getString("partial")

        if (text.isNotEmpty()) {
            view.previewText.text = text + " ..."
        }
    }

    override fun onResult(result: String) {
        val obj = JSONObject(result)
        val text = obj.getString("text")

        if (text.isNotEmpty()) {
            speechService?.stop()
            speechService = null

            view.statusText.text = "Got it!"
            view.previewText.text = text

            SpokenEvent::class.java.requestQuery(
                this,
                SpokenEventOutput(eventId, text)
            )
            finish()
        }
    }

    override fun onFinalResult(result: String) {
        /* empty */
    }

    override fun onError(error: Exception) {
        view.statusText.text = "Error: " + error.message
    }

    override fun onTimeout() {
        view.statusText.text = "Timed out"
    }

}