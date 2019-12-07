package edu.uw.tcss450.tcss450_group4.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.tcss450_group4.R;
import edu.uw.tcss450.tcss450_group4.model.Message;
public class MyMessageListRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageListRecyclerViewAdapter.ViewHolder> {
    private List<Message> mValues;
    private final ChatFragment.OnListFragmentInteractionListener mListener;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private int mMemberId;
    private int mViewType;
    public MyMessageListRecyclerViewAdapter(List<Message> messageList, int memberId, ChatFragment.OnListFragmentInteractionListener listener) {
        mMemberId = memberId;
        mListener = listener;
        mValues = messageList;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mViewType == 2) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.their_message, parent, false);
            return new ViewHolder(view);
        } else if ( mViewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_message, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }


    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        switch (mViewType) {
            case 2:
                holder.mItem = mValues.get(position);
                holder.mUsername.setText(mValues.get(position).getUsername());
                holder.mMessage.setText(mValues.get(position).getMessage());

                holder.mAvatar.setImageBitmap(convertToBitmap(mValues.get(position).getProfileUri()));
                break;
            case 1:
                holder.mItem = mValues.get(position);
                holder.mMessage.setText(mValues.get(position).getMessage());
                break;
        }
        Log.d("MESSAGE", "onBindViewHolder() position: " + position);

    }

    private Bitmap convertToBitmap (String profileUri) {
        String cleanImage = profileUri.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
        byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addMessage(String userName, int memberId, String message, String timeStamp, String profileUri) {
        Message newMess = new Message.Builder(userName, memberId, message, timeStamp, profileUri).build();
        mValues.add(newMess);
    }
    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mValues.get(position);

        if (Integer.valueOf(message.getMemberId()) == mMemberId) {
            // If the current user is the sender of the message
            return mViewType = 1;
        } else {
            // If some other user sent the message
            return mViewType = 2;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMessage;
        public final TextView mUsername;
        public Message mItem;
        public final ImageView mAvatar;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            switch (mViewType){
                case 1:
                    mUsername = null;
                    mMessage = view.findViewById(R.id.txt_myMessage);
                    mAvatar = null;
                    break;
                    default:
                        mUsername = view.findViewById(R.id.txt_friendUserName);
                        mMessage = view.findViewById(R.id.txt_theirMessage);
                        mAvatar = view.findViewById(R.id.avatar);
                        break;
            }
        }

        @Override
        public String toString() {
            return super.toString() + mUsername.getText() + " '" + mMessage.getText() + "'";
        }
    }
}
