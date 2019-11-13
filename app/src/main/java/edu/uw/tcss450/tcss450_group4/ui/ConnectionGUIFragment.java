package edu.uw.tcss450.tcss450_group4.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.tcss450_group4.R;
import edu.uw.tcss450.tcss450_group4.model.ConnectionItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ConnectionGUIFragment extends Fragment {

    private List<ConnectionItem> mConnection;
    private String mJwToken;
    private int mMemberId;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConnectionGUIFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ConnectionGUIFragment newInstance(int columnCount) {
        ConnectionGUIFragment fragment = new ConnectionGUIFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionGUIFragmentArgs args = ConnectionGUIFragmentArgs.fromBundle(getArguments());
        mJwToken = args.getJwt();
        mMemberId = args.getMemberid();
        mConnection = new ArrayList<>(Arrays.asList(args.getConnectionitems()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connectiongui_list, container, false);

        // Set the adapter

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.list);
        if (rv instanceof RecyclerView ) {
            Context context = rv.getContext();
            RecyclerView recyclerView = (RecyclerView) rv;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyConnectionGUIRecyclerViewAdapter(mConnection, this::displayConnection));
        }
    }

    private void displayConnection(ConnectionItem theConnection) {

        final Bundle args = new Bundle();
        args.putSerializable(getString(R.string.keys_connection_view), theConnection);
        args.putString("jwt", mJwToken);
        args.putInt("memberid", mMemberId);
        Navigation.findNavController(getView())
                .navigate(R.id.action_nav_connectionGUI_to_viewConnectionFragment, args);
    }



//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ConnectionItem item);
    }
}
