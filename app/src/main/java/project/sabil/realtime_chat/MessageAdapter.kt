package project.sabil.realtime_chat

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MessageAdapter(context: Context, private val listMessage: MutableList<Message?>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.message_item, parent, false)

        val token = rowView.findViewById(R.id.token) as TextView
        val message = rowView.findViewById(R.id.message) as TextView

        token.text = listMessage[position]?.token?.substring(0, 10)
        message.text = listMessage[position]?.message

        return rowView
    }

    override fun getItem(position: Int): Message? {
        return listMessage[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listMessage.size
    }

}