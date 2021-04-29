package li.ebc.vosk4tasker.tasker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import li.ebc.vosk4tasker.R
import li.ebc.vosk4tasker.VOSKActivity
import li.ebc.vosk4tasker.databinding.ActivitySpeaknowConfigureBinding

@TaskerInputRoot
class SpeakNowInput @JvmOverloads constructor(
    @field:TaskerInputField("prompt", R.string.speaknow_input_prompt) var prompt: String = "",
    @field:TaskerInputField("event_id", R.string.speaknow_input_eventid) var eventId: String = "",
)

/*@TaskerOutputObject
class SpeakNowOutput(
    @get:TaskerOutputVariable("text", R.string.speaknow_out_text, R.string.speaknow_out_text_desc) var text: String,
)*/

class SpeakNowActionHelper(config: TaskerPluginConfig<SpeakNowInput>) :
    TaskerPluginConfigHelperNoOutput<SpeakNowInput, SpeakNowActionRunner>(config) {

    override val inputClass = SpeakNowInput::class.java
    override val runnerClass = SpeakNowActionRunner::class.java

    override val defaultInput get() = SpeakNowInput("Speak Now...")

    override fun addToStringBlurb(input: TaskerInput<SpeakNowInput>, blurbBuilder: StringBuilder) {
        blurbBuilder.appendLine("\nPrompts user for text to speech")
    }
}

class SpeakNowActionRunner : TaskerPluginRunnerActionNoOutput<SpeakNowInput>() {
    override fun run(
        context: Context,
        input: TaskerInput<SpeakNowInput>
    ): TaskerPluginResult<Unit> {
        val intent = Intent(context, VOSKActivity::class.java).apply {
            putExtra("prompt", input.regular.prompt)
            putExtra("event_id", input.regular.eventId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        }

        context.startActivity(intent)

        return TaskerPluginResultSucess()
    }
}

class SpeakNowAction : ActivityConfigTaskerNoOutput<SpeakNowInput, SpeakNowActionRunner, SpeakNowActionHelper>() {
    lateinit var view: ActivitySpeaknowConfigureBinding
    var promptText = ""
    var eventText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySpeaknowConfigureBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.promptBox.setText(promptText)
        view.idBox.setText(eventText)
    }

    override val layoutResId = R.layout.activity_speaknow_configure
    override val inputForTasker: TaskerInput<SpeakNowInput>
        get() = TaskerInput(SpeakNowInput(view.promptBox.text.toString(), view.idBox.text.toString()))

    override fun assignFromInput(input: TaskerInput<SpeakNowInput>) {
        promptText = input.regular.prompt
        eventText = input.regular.eventId
    }

    override fun getNewHelper(config: TaskerPluginConfig<SpeakNowInput>) = SpeakNowActionHelper(config)
}