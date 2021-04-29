package li.ebc.vosk4tasker.tasker

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionEvent
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionUnsatisfied
import li.ebc.vosk4tasker.R
import li.ebc.vosk4tasker.databinding.ActivitySpeaknowConfigureBinding
import li.ebc.vosk4tasker.databinding.ActivitySpokenConfigureBinding


@TaskerInputRoot
class SpokenEventFilter @JvmOverloads constructor(
    @field:TaskerInputField(
        "event_id",
        labelResId = R.string.speaknow_input_eventid
    ) val eventId: String = "",
)

@TaskerInputRoot
@TaskerOutputObject
class SpokenEventOutput @JvmOverloads constructor(
    val eventId: String = "",

    @field:TaskerInputField("requestQuery") @get:TaskerOutputVariable(
        "text",
        R.string.speaknow_out_text,
        R.string.speaknow_out_text_desc
    ) val text: String = ""
)

class SpokenEventRunner : TaskerPluginRunnerConditionEvent<SpokenEventFilter, SpokenEventOutput, SpokenEventOutput>() {
    override fun getSatisfiedCondition(
        context: Context,
        input: TaskerInput<SpokenEventFilter>,
        update: SpokenEventOutput?
    ): TaskerPluginResultCondition<SpokenEventOutput> {
        if (update?.eventId != input.regular.eventId)
            return TaskerPluginResultConditionUnsatisfied()

        return TaskerPluginResultConditionSatisfied(context, update)
    }
}

class SpokenEventHelper(config: TaskerPluginConfig<SpokenEventFilter>) :
    TaskerPluginConfigHelper<SpokenEventFilter, SpokenEventOutput, SpokenEventRunner>(config) {

    override val inputClass = SpokenEventFilter::class.java
    override val outputClass = SpokenEventOutput::class.java
    override val runnerClass = SpokenEventRunner::class.java
}

class SpokenEvent: ActivityConfigTasker<SpokenEventFilter, SpokenEventOutput, SpokenEventRunner, SpokenEventHelper>() {
    lateinit var view: ActivitySpokenConfigureBinding
    var eventText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySpokenConfigureBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.idBox2.setText(eventText)
    }

    override val layoutResId = R.layout.activity_speaknow_configure
    override val inputForTasker: TaskerInput<SpokenEventFilter>
        get() = TaskerInput(SpokenEventFilter())

    override fun assignFromInput(input: TaskerInput<SpokenEventFilter>) {
        eventText = input.regular.eventId
    }

    override fun getNewHelper(config: TaskerPluginConfig<SpokenEventFilter>) = SpokenEventHelper(config)
}
