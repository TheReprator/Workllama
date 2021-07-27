package app.template.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class DBContactEntity(
    @PrimaryKey val id: Long, val name: String, val phone: String,
    val thumbnail: String, val email: String, val isStarred: Boolean, val page: Long
)