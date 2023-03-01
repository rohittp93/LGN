package com.lgn.presentation.ui.utils

import java.util.*

sealed class AddEventFormEvent {
    data class EventNameChanged(val eventName: String) : AddEventFormEvent()
    data class EventDescriptionChanged(val eventDescription: String) : AddEventFormEvent()
    data class EventStartTimeChanged(val eventStartTime: Date) : AddEventFormEvent()
    data class EventEndTimeChanged(val eventEndTime: Date) : AddEventFormEvent()
    data class EventBookingStartTimeChanged(val bookingStartTime: Date) : AddEventFormEvent()
    data class StagsAllowedChanged(val stagsAllowed: Boolean) : AddEventFormEvent()
    data class RequiresBookingChanged(val requiresBooking: Boolean) : AddEventFormEvent()
    data class CouplesAllowedChanged(val couplesAllowed: Boolean) : AddEventFormEvent()

    data class StagCapacityChanged(val stagCapacity: Int) : AddEventFormEvent()
    data class StagsPerBookingChanged(val stagsPerBooking: Int) : AddEventFormEvent()
    data class StagPriceChanged(val stagPrice: Double) : AddEventFormEvent()

    data class CoupleCapacityChanged(val coupleCapacity: Int) : AddEventFormEvent()
    data class CouplesPerBookingChanged(val couplesPerBooking: Int) : AddEventFormEvent()
    data class CouplePriceChanged(val couplePrice: Double) : AddEventFormEvent()

    object Submit: AddEventFormEvent()
}
