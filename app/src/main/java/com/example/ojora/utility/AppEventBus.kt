package com.example.ojora.utility

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class EventType {
    SIGN_IN_REQUIRED,
    FORCE_LOGOUT,
    BOOKING_UPDATED
}

/**
 * Event bus for publishing application-wide events.
 */
object AppEventBus {
    private val _events = MutableSharedFlow<EventType>(extraBufferCapacity = 64)
    val events: SharedFlow<EventType> = _events.asSharedFlow()

    fun publish(event: EventType) {
        _events.tryEmit(event)
    }
}
