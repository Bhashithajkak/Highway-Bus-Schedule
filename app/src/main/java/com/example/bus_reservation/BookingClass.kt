package com.example.bus_reservation

data class BookingClass(
    private var dataName: String,
    private var dataEmail: String,
    private var dataPhoneNumber: String,
    private var dataFrom: String,
    private var dataTo: String,
    private var dataBusName: String
) {
    // Add getter methods for other fields as needed
    fun getDataName(): String = dataName
    fun getDataEmail(): String = dataEmail

    fun getDataPhoneNumber():String=dataPhoneNumber

    fun getDataFrom(): String=dataFrom

    fun getDataTo(): String=dataTo

    fun getDataBusName(): String=dataBusName
    // Add other getter methods for additional fields

    constructor() : this("", "", "", "", "", "")
}
