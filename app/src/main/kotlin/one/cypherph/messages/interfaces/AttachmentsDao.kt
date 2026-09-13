package one.cypherph.messages.interfaces

import androidx.room.Dao
import androidx.room.Query
import one.cypherph.messages.models.Attachment

@Dao
interface AttachmentsDao {
    @Query("SELECT * FROM attachments")
    fun getAll(): List<Attachment>
}
