package com.lgn.di

import com.lgn.data.RemoteRepositoryImpl
import com.lgn.domain.repository.Repository
import com.lgn.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

//TODO: Add retrofit related dependencies
@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object AppModule {
    /*@Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()*/

    /*@Provides
    fun provideUsersRef(db: FirebaseFirestore) = db.collection(USERS)

    @Provides
    fun provideAuthenticatedAdminRef(db: FirebaseFirestore) = db.collection(AUTHENTICATED_ADMINS)

    @Provides
    fun provideAuthAdminQuery(userReference: CollectionReference) = userReference.orderBy(EMAIL)*/

    /* @Provides
     fun provideFirebaseAuth() = FirebaseAuth.getInstance()

     @Provides
     fun provideFirebaseStorage() =  FirebaseStorage.getInstance()

     @Provides
     fun provideUserRepository(
         auth: FirebaseAuth,
         fireStore: FirebaseFirestore,
         firebaseStorage: FirebaseStorage
     ): AuthRepository = AuthRepositoryImpl(auth, fireStore, firebaseStorage)*/

    @Provides
    fun provideRepository(): Repository = RemoteRepositoryImpl()

    @Provides
    fun provideUseCases(repository: Repository) = UseCases(
        loginUser = LoginUser(repository),
        logoutUser = LogoutUser(repository),
        validateUserCode = ValidateUserCode(),
        checkAuthStatus = CheckAuthStatus(repository),
        validatePassword = ValidatePassword(),
        fetchTeam = FetchTeam(repository),
        fetchStudentMetrics = FetchStudentMetrics(repository),
        fetchStudents = FetchStudents(repository),
        updateStudentMetrics = UpdateStudentMetrics(repository),
        fetchStudentProfileMetrics = FetchStudentProfileMetrics(repository),
        changeToGraduate = ChangeToGraduate(repository),
        updateStudentStatus = UpdateStudentStatus(repository),
        getUserProfileDetails = GetUserProfileDetails(repository),
        addStudent = AddStudent(repository)
    )
}