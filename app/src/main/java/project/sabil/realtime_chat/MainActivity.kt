package project.sabil.realtime_chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val token = FirebaseInstanceId.getInstance().token
    private val listMessage: MutableList<Message?> = mutableListOf()
    private val listAdapter: MessageAdapter by lazy {
        MessageAdapter(this, listMessage)
    }

    init {
        Log.d("TOKEN", token)
        databaseReference
            .child("Group")
            .child("Message")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listMessage.clear()
                    for (messageSnapshot in dataSnapshot.children) {
                        listMessage.add(getMessageFromDataSnapshot(messageSnapshot))
                        listAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list_message.adapter = listAdapter
        delete_message_database.setOnClickListener { resetMessageDatabase() }
        send_message_database.setOnClickListener { sendMessageData() }
    }

    private fun getMessageFromDataSnapshot(dataSnapshot: DataSnapshot): Message {
        val token = dataSnapshot.child("token").value as String?
        val message = dataSnapshot.child("message").value as String?
        val title = dataSnapshot.child("title").value as String?
        val group = dataSnapshot.child("group").value as String?
        return Message(token, title, group, message)
    }

    private fun sendMessageData() {
        databaseReference.child("Group")
            .child("Message")
            .child(Calendar.getInstance().time.toString())
            .setValue(
                Message(
                    token.toString(),
                    "Title Test",
                    Calendar.getInstance().time.toString(),
                    message_value.text.toString()
                )
            )
    }

    private fun resetMessageDatabase() {
        databaseReference.setValue(null)
        listMessage.clear()
        listAdapter.notifyDataSetChanged()
    }
}
