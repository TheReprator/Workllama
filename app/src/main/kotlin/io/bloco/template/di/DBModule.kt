package io.bloco.template.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.template.database.DBContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.bloco.template.implementation.AppRoomDb
import app.template.database.DBManager
import app.template.database.DBManagerImpl
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    companion object {
        private const val DATABASE_NAME = "workllama_database"
    }

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Timber.d("RoomDatabaseModule onCreate")
        }
    }

    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): AppRoomDb {
        return Room.databaseBuilder(context, AppRoomDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
    }

    @Singleton
    @Provides
    fun providesCategoryDAO(appRoomDb: AppRoomDb): DBContactDao =
        appRoomDb.contactDao()

    @Singleton
    @Provides
    fun providesDBManager(dbContactDao: DBContactDao): DBManager =
        DBManagerImpl(dbContactDao)
}