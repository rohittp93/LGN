package com.lgn.presentation.ui.utils

import java.util.*

data class AddEventFormState(
    val eventID: String = "",
    val eventName: String = "",
    val eventNameError: String? = "",
    val eventDescription: String = "",
    val eventImageURL: String = "",
    val eventDescriptionError: String? = "",

    val eventStartTime: Date = Calendar.getInstance().time,
    val eventEndTime: Date = Calendar.getInstance().time,
    val bookingStartTime: Date = Calendar.getInstance().time,

    var stagsAllowed: Boolean = false,
    var requiresBooking: Boolean = true,
    val stagsCapacity: Int = 0,
    val stagsCapacityError: String? = "",

    val stagsPerBooking: Int = 0,
    val stagsPerBookingError: String? = "",

    val stagPrice: Double = 0.0,
    val stagsPriceError: String? = "",

    var couplesAllowed: Boolean = false,

    val couplesCapacity: Int = 0,
    val couplesCapacityError: String? = "",

    val couplesPerBooking: Int = 0,
    val couplesPerBookingError: String? = "",

    val couplePrice: Double = 0.0,
    val couplesPriceError: String? = "",
    val stagCapacityError: String = "",
    val coupleCapacityError: String = "",


    var isUpdating: Boolean = false
)