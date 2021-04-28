package li.ebc.vosk4tasker.tasker

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import li.ebc.vosk4tasker.R
import li.ebc.vosk4tasker.databinding.ActivitySpeaknowConfigureBinding

@TaskerInputRoot
class SpeakNowInput @JvmOverloads constructor(
    @field:TaskerInputField("prompt", R.string.speaknow_input_prompt) var prompt: String = "",
)

@TaskerOutputObject
class SpeakNowOutput(
    @get:TaskerOutputVariable("text", R.string.speaknow_out_text, R.string.speaknow_out_text_desc) var text: String,
)

class SpeakNowActionHelper(config: TaskerPluginConfig<SpeakNowInput>) :
    TaskerPluginConfigHelper<SpeakNowInput, SpeakNowOutput, SpeakNowActionRunner>(config) {

    override val inputClass = SpeakNowInput::class.java
    override val outputClass = SpeakNowOutput::class.java
    override val runnerClass = SpeakNowActionRunner::class.java

    override val defaultInput get() = SpeakNowInput("Speak Now...")

    override fun addToStringBlurb(input: TaskerInput<SpeakNowInput>, blurbBuilder: StringBuilder) {
        blurbBuilder.appendLine("\nPrompts user for text to speech")
    }
}

class SpeakNowActionRunner : TaskerPluginRunnerAction<SpeakNowInput, SpeakNowOutput>() {
    override fun run(
        context: Context,
        input: TaskerInput<SpeakNowInput>
    ): TaskerPluginResult<SpeakNowOutput> {
        val prompt = input.regular.prompt
        // TODO: the entire "speak now" thingy here
        val output = SpeakNowOutput("lorem ipsum")
        return TaskerPluginResultSucess(output)
    }
}

class SpeakNowAction : ActivityConfigTasker<SpeakNowInput, SpeakNowOutput, SpeakNowActionRunner, SpeakNowActionHelper>() {
    lateinit var view: ActivitySpeaknowConfigureBinding
    var promptText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySpeaknowConfigureBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.promptBox.setText(promptText)
    }

    override val layoutResId = R.layout.activity_speaknow_configure
    override val inputForTasker: TaskerInput<SpeakNowInput>
        get() = TaskerInput(SpeakNowInput(view.promptBox.text.toString()))

    override fun assignFromInput(input: TaskerInput<SpeakNowInput>) {
        promptText = input.regular.prompt
    }

    override fun getNewHelper(config: TaskerPluginConfig<SpeakNowInput>) = SpeakNowActionHelper(config)
}