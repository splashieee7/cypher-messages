package one.cypherph.messages.interfaces

import androidx.room.Dao
import androidx.room.Query
import one.cypherph.messages.models.MessageAttachment

@Dao
interface MessageAttachmentsDao {
    @Query("SELECT * FROM message_attachments")
    fun getAll(): List<MessageAttachment>
}
