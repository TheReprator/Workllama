package io.bloco.template.implementation

import androidx.room.Database
import androidx.room.RoomDatabase
import app.template.database.DBContactDao
import app.template.database.DBContactEntity

@Database(
    entities = [DBContactEntity::class],
    version = 1, exportSchema = true
)
abstract class AppRoomDb : RoomDatabase() {
    abstract fun contactDao(): DBContactDao
}

