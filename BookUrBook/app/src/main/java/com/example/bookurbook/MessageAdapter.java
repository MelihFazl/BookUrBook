package com.example.bookurbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter<currentChat> extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<Message> messages;
    private Chat currentChat;

    public MessageAdapter(Context c, ArrayList<Message> messages, Chat currentChat)
    {
        this.context = c;
        this.messages = messages;
        this.currentChat = currentChat;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if ( viewType == MSG_TYPE_RIGHT)
        {
            View view = layoutInflater.inflate(R.layout.message_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = layoutInflater.inflate(R.layout.message_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {
        Message msg = messages.get(position);

        holder.showMessage.setText(msg.getContent());
        holder.showDate.setText(msg.getMessageDate() + " " + msg.getMessageTime());
        Picasso.get().load(currentChat.getUser2().getAvatar()).into(holder.messageImage);
    }

    @Override
    public int getItemCount()
    {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //variables
        private TextView showMessage;
        private TextView showDate;
        private ImageView messageImage;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            showMessage = itemView.findViewById(R.id.show_message);
            messageImage = itemView.findViewById(R.id.message_picture);
            showDate = itemView.findViewById(R.id.show_date);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if ( messages.get(position).getSentBy().equals(currentChat.getUser1().getUsername()) )
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
