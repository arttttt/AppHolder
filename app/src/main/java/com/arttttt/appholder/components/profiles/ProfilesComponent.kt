package com.arttttt.appholder.components.profiles

import com.arkivanov.decompose.value.Value
import com.arttttt.appholder.arch.shared.DecomposeComponent
import com.arttttt.appholder.arch.shared.context.AppComponentContext
import com.arttttt.appholder.arch.shared.events.consumer.EventsConsumer
import com.arttttt.appholder.arch.shared.events.producer.EventsProducer
import com.arttttt.appholder.domain.entity.profiles.Profile
import com.arttttt.appholder.ui.base.ListItem
import kotlinx.coroutines.flow.StateFlow

interface ProfilesComponent : DecomposeComponent,
    AppComponentContext,
    EventsProducer<ProfilesComponent.Events.Output>,
    EventsConsumer<ProfilesComponent.Events.Input> {

    data class State(
        val currentProfile: Profile?,
        val isCurrentProfileDirty: Boolean,
    )

    data class UiState(
        val items: List<ListItem>,
    )

    sealed class Events {

        sealed class Output : Events()

        sealed class Input {

            data object MarkCurrentProfileAsDirty : Input()
            data object UpdateCurrentProfile : Input()
        }
    }

    val uiState: Value<UiState>

    val states: StateFlow<State>

    fun addProfile(title: String, color: Int)
    fun removeProfile(id: String)
    fun profileClicked(id: String)
}