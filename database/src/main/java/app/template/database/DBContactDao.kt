package app.template.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DBContactDao {

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContact(id: Long): DBContactEntity

    @Query("SELECT * FROM contact ORDER BY page LIMIT :count OFFSET :offset")
    fun getContactList(count: Int, offset: Int): Flow<List<DBContactEntity>>

    @Delete
    suspend fun deleteContact(DBContactEntity: DBContactEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: DBContactEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entity: DBContactEntity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<DBContactEntity>): List<Long>

    @Query("UPDATE contact SET isStarred=:isStarred WHERE id = :id")
    suspend fun update(isStarred: Boolean, id: Long)

    @Delete
    suspend fun deleteEntity(entity: DBContactEntity): Int

    @Query("DELETE FROM contact")
    suspend fun clearTable(): Int
}
