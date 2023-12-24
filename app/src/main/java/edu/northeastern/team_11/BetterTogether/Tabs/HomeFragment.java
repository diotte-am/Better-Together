package edu.northeastern.team_11.BetterTogether.Tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.team_11.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String username;
    private String mParam2;

    private TextView medView;
    private TextView affView;
    private TextView yogaView;
    private TextView journView;

    private RecyclerView recycleview;
    private DatabaseReference myDatabase;
    private DatabaseReference usersReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    private List<BTPost> postfeed;



    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(String username) {
        this.username = username;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("UN", "Username" + username);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        usersReference = FirebaseDatabase.getInstance()
                .getReference("/BT/users");
        postfeed = new ArrayList<>();
        populateFeed();

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        medView = view.findViewById(R.id.bt_home_streaks_textview_med);
        affView = view.findViewById(R.id.bt_home_streaks_textview_aff);
        yogaView = view.findViewById(R.id.bt_home_streaks_textview_yoga);
        journView = view.findViewById(R.id.bt_home_streaks_textview_journ);

        populateFeed();
        recycleview = view.findViewById(R.id.BT_recyclerView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        //    recycleview.setHasFixedSize(true);
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(postfeed, getContext());
        recycleview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot user = snapshot.child("BT").child("users").child(username);
                BTUser returningUser = user.child("info").getValue(BTUser.class);
                String medCount = returningUser.getMedStreakCount().toString();
                String affCount = returningUser.getAffStreakCount().toString();
                String yogaCount = returningUser.getYogaStreakCount().toString();
                String journCount = returningUser.getJournStreakCount().toString();
                medView.setText("Meditations Streak: " + medCount);
                affView.setText("Affirmations Streak: " + affCount);
                yogaView.setText("Yoga Practices Streak: " + yogaCount);
                journView.setText("Journaling Streak: " + journCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateFeed();
        recycleview = view.findViewById(R.id.BT_recyclerView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
    //    recycleview.setHasFixedSize(true);
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(postfeed, getContext());
        recycleview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private void populateFeed(){
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> users = snapshot.getChildren();
                users.forEach(u -> {
                    String username =  u.getKey();
                    Iterable<DataSnapshot> posts = snapshot.child(username).child("posts").getChildren();
                    posts.forEach(p ->{
                        BTPost post = p.getValue(BTPost.class);
                        postfeed.add(post);
                    });
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}