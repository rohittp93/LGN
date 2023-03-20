package com.lgn.domain.repository

import android.content.Context
import com.lgn.domain.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loginWithUserCodeAndPassword(
        userCode: String,
        password: String,
        context: Context,
        scope: CoroutineScope
    ): Flow<Response<AuthResult>>

    fun logoutUser(context: Context): Flow<Response<Boolean>>

    fun isUserLoggedIn(context: Context): Boolean

    fun getUserProfileDetails(context: Context): UserProfile

    fun fetchTeam(context: Context): Flow<Response<TeamData>>

    fun fetchStudents(context: Context, monthYear: String): Flow<Response<List<Users>>>

    fun fetchStudentMetrics(context: Context, id: String): Flow<Response<StudentMerticsResponse>>

    fun updateStudentMetrics(context: Context, studentMetrics: StudentMerticsResponse): Flow<Response<StudentMerticsResponse>>

    fun fetchStudentProfileMetrics(context: Context, userId: String, year: String): Flow<Response<StudentProfileMerticsResponse>>

    fun changeToGraduate(context: Context, userId: String, batch: String): Flow<Response<UpdateStudentResponse>>

    fun updateStudentStatus(context: Context, userId: String, status: Int, role: String): Flow<Response<UpdateStudentResponse>>

    fun addStudent(context: Context, student: UpdateStudentResponse): Flow<Response<UpdateStudentResponse>>

    /*suspend fun firebaseRegisterWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Void?>>

    fun isUserLoggedIn(): Boolean

    fun getUser(context: Context): AuthAdmin

    suspend fun addTable(context: Context, table: TableData, isUpdating: Boolean = false): Flow<Response<Void>>

    suspend fun addVenue(context: Context, venueData: VenueData, isUpdating: Boolean = false): Flow<Response<Void>>

    suspend fun addEvent(context: Context, table: EventData, isUpdating: Boolean = false): Flow<Response<Void>>

    suspend fun deleteEvent(context: Context, eventId: String): Flow<Response<Boolean>>

    fun fetchTablesFromFirestore(context: Context): Flow<Response<List<TableData>>>

    fun fetchEventsFromFirestore(context: Context): Flow<Response<List<EventData>>>

    fun fetchAllEventsFromFirestore(context: Context): Flow<Response<List<EventData>>>

    fun fetchVenueDetailsFromFirestore(context: Context): Flow<Response<VenueData?>>

    fun fetchEventTablesFromFirestore(context: Context): Flow<Response<List<EventTableData>>>

    fun fetchBookings(context: Context, eventId: String): Flow<Response<List<BookingData>>>*/
}