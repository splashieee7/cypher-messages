package one.cypherph.messages.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.fossify.commons.extensions.notificationManager
import org.fossify.commons.helpers.ensureBackgroundThread
import one.cypherph.messages.extensions.deleteMessage
import one.cypherph.messages.extensions.updateLastConversationMessage
import one.cypherph.messages.helpers.IS_MMS
import one.cypherph.messages.helpers.MESSAGE_ID
import one.cypherph.messages.helpers.THREAD_ID
import one.cypherph.messages.helpers.refreshConversations
import one.cypherph.messages.helpers.refreshMessages

class DeleteSmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val threadId = intent.getLongExtra(THREAD_ID, 0L)
        val messageId = intent.getLongExtra(MESSAGE_ID, 0L)
        val isMms = intent.getBooleanExtra(IS_MMS, false)
        context.notificationManager.cancel(threadId.hashCode())
        ensureBackgroundThread {
            context.deleteMessage(messageId, isMms)
            context.updateLastConversationMessage(threadId)
            refreshMessages()
            refreshConversations()
        }
    }
}
