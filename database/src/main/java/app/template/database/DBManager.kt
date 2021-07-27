package app.template.database

import app.template.base.useCases.AppResult
import app.template.base.useCases.AppSuccess
import app.template.base.util.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface DBManager {
    suspend fun saveContact(dbContactEntity: DBContactEntity): AppResult<Long>
    suspend fun saveContactList(dbContactEntity: List<DBContactEntity>): AppResult<List<Long>>
    suspend fun getContact(id: Long): DBContactEntity
    suspend fun getContactList(count: Int, offset: Int): Flow<List<DBContactEntity>>
    suspend fun deleteContact(dbContactEntity: DBContactEntity): AppResult<Int>
    suspend fun updateContact(isStarred: Boolean, id: Long): AppResult<Unit>
    suspend fun clearTable(): AppResult<Int>
}

@Singleton
class DBManagerImpl @Inject constructor(private val dbContactDao: DBContactDao) : DBManager {

    private suspend fun saveContactDB(dBContactEntity: DBContactEntity): AppResult<Long> {
        val data = dbContactDao.insert(dBContactEntity)
        return AppSuccess(data)
    }

    override suspend fun saveContact(dbContactEntity: DBContactEntity): AppResult<Long> =
        safeApiCall(call =
        { saveContactDB(dbContactEntity) })

    private suspend fun saveContactListDB(dBContactEntity: List<DBContactEntity>): AppResult<List<Long>> {
        val data = dbContactDao.insertAll(dBContactEntity)
        return AppSuccess(data)
    }

    override suspend fun saveContactList(dbContactEntity: List<DBContactEntity>): AppResult<List<Long>> =
        safeApiCall(call =
        { saveContactListDB(dbContactEntity) })

    override suspend fun getContact(id: Long): DBContactEntity =
        dbContactDao.getContact(id)

    override suspend fun getContactList(count: Int, offset: Int): Flow<List<DBContactEntity>> {
        println("fetch from localDB 2")
        return dbContactDao.getContactList(count, offset)
    }

    private suspend fun deleteContactDB(dBContactEntity: DBContactEntity): AppResult<Int> {
        val data = dbContactDao.deleteContact(dBContactEntity)
        return AppSuccess(data)
    }

    override suspend fun deleteContact(dbContactEntity: DBContactEntity): AppResult<Int> =
        safeApiCall(call =
        { deleteContactDB(dbContactEntity) })

    private suspend fun updateContactDB(isStarred: Boolean, id: Long): AppResult<Unit> {
        val data = dbContactDao.update(isStarred, id)
        return AppSuccess(data)
    }

    override suspend fun updateContact(isStarred: Boolean, id: Long): AppResult<Unit> =
        safeApiCall(call =
        { updateContactDB(isStarred, id) })

    private suspend fun deleteAllContactDB(): AppResult<Int> {
        val longList = dbContactDao.clearTable()
        return AppSuccess(longList)
    }

    override suspend fun clearTable(): AppResult<Int> = safeApiCall(call =
    { deleteAllContactDB() })
}