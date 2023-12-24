package edu.northeastern.team_11.BetterTogether.Tabs;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.team_11.BetterTogether.BTLandingPage;
import edu.northeastern.team_11.BetterTogether.BTProfile;
import edu.northeastern.team_11.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button profileBtn;

    View view;
    BTLandingPage BTLanding;
    ArrayList<BTUser> newList;
    TextView newUser1, newUser2, searchUser;
    EditText usernameSearch;
    Button profile, editProfile, user1Add, user1Profile, user2Add, user2Profile,
    searchButton, searchUserAdd, searchUserProfile;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference, friendReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DataSnapshot dbSnapshot;
    ArrayList<BTUser> userList;
    ArrayList<String> friendIds, allUsernames;
    BTUser currentUser;
    AutoCompleteTextView autoTextView;
    ConstraintLayout mlayout;




    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("/BT");

        friendReference = FirebaseDatabase.getInstance().getReference("/BT/users");

        userList =new ArrayList<>();
        friendIds = new ArrayList<>();
        allUsernames = new ArrayList<>();
        databaseReference.orderByChild("users/info/joined").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Iterable<DataSnapshot> users = snapshot.getChildren();
                users.forEach(u -> {
                    String username =  u.getKey();
                    allUsernames.add(username);
                    if(!username.equals(BTLanding.getUser().getUserName())){
                        BTUser user = new BTUser(snapshot.child(username).child("info").getValue(BTUser.class));
                        userList.add(user);
                    } else if(username.equals(BTLanding.getUser().getUserName())) {
                        Iterable<DataSnapshot> friends = snapshot.child(username).child("friends").getChildren();
                        friends.forEach(f -> {
                            String id = f.getValue(String.class);
                            friendIds.add(id);
                        });
                    }

                });

                BTLanding.setUserList(userList);

                newList = new ArrayList<>();

                for(BTUser newUser : userList){
                   if(!isFriends(newUser)) {
                       newList.add(newUser);
                   }
                   if(isFriends(newUser)){
                   }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.select_dialog_item, allUsernames);
                autoTextView = view.findViewById(R.id.usernameSearch);
                autoTextView.setThreshold(1);
                autoTextView.setAdapter(adapter);





                newUser1 = view.findViewById(R.id.card1Username);
                BTUser user1 = new BTUser(newList.get(1));
                newUser1.setText(user1.getFirstName() + " " + user1.getLastName() +"\n" + user1.getCity() + ", " + user1.getCountry());

                newUser2 = view.findViewById(R.id.card2Username);
                BTUser user2 = new BTUser(newList.get(2));
                newUser2.setText(user2.getFirstName() + " " + user2.getLastName() + "\n" + user2.getCity() + ", " + user2.getCountry());

                searchButton = view.findViewById(R.id.userSearchButton);
                searchUser = view.findViewById(R.id.usernameSearch);
                mlayout = view.findViewById(R.id.searchResults);


                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String q = autoTextView.getText().toString();
                                if(snapshot.child("users").hasChild(q)){
                                    mlayout.setVisibility(View.VISIBLE);
                                    BTUser foundUser = snapshot.child("users").child(q).child("info").getValue(BTUser.class);
                                    BTLanding.setUser(currentUser);
                                    BTLanding.openProfile(view, foundUser);

                                } else {
                                    mlayout.setVisibility(View.INVISIBLE);
                                    Toast.makeText(view.getContext(), "No user find with this name", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });




                user1Profile = view.findViewById(R.id.card1ProfileButton);
                user1Profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BTLanding.openProfile(view, user1);


                    }
                });

                user1Add = view.findViewById(R.id.card1AddButton);
                if(isFriends(user1)){
                    user1Add.setBackgroundColor(Color.parseColor("#FF0000"));
                };
                user1Add.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (isFriends(user1)){
                            friendIds.remove(user1.getId());
                            friendReference.child(currentUser.getUserName()).child("friends").setValue(friendIds);
                            user1Add.setBackgroundColor(Color.parseColor("#FFB800"));
                            Toast.makeText(view.getContext(), "You have unfollowed this user", Toast.LENGTH_SHORT).show();
                        } else {
                            friendIds.add(user1.getId());
                            friendReference.child(currentUser.getUserName()).child("friends").push().setValue(user1.getId());
                            user1Add.setBackgroundColor(Color.parseColor("#FF0000"));
                            Toast.makeText(view.getContext(), "You are now following this user.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                user2Add = view.findViewById(R.id.card2AddButton);
                if(isFriends(user2)){
                    user2Add.setBackgroundColor(Color.parseColor("#FF0000"));
                };
                user2Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isFriends(user2)){
                            friendIds.remove(user2.getId());
                            friendReference.child(currentUser.getUserName()).child("friends").setValue(friendIds);
                            user2Add.setBackgroundColor(Color.parseColor("#FFB800"));
                            Toast.makeText(view.getContext(), "You have unfollowed this user", Toast.LENGTH_SHORT).show();
                        } else {
                            friendIds.add(user2.getId());
                            friendReference.child(currentUser.getUserName()).child("friends").push().setValue(user2.getId());
                            user2Add.setBackgroundColor(Color.parseColor("#FF0000"));
                            Toast.makeText(view.getContext(), "You are now following this user.", Toast.LENGTH_SHORT).show();
                        }}
                });

                user2Profile = view.findViewById(R.id.card2ProfileButton);
                user2Profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BTLanding.openProfile(view, user2);
                    }
                });




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);


        BTLanding = (BTLandingPage) getActivity();
        currentUser = BTLanding.getUser();

        ArrayList<BTUser> users = BTLanding.getUserList();

        searchUser = view.findViewById(R.id.searchUsername);
        searchUser.setText("Please enter username.");

        profileBtn = (Button) view.findViewById(R.id.profileButton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BTLanding.openProfile(view, currentUser);
            }
        });

        return view;
    }

    private Boolean isFriends(BTUser user){
        for (String id : friendIds){
            if(user.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    private BTUser createReturningUser(DataSnapshot user) {

        BTUser returningUser = user.child("info").getValue(BTUser.class);
        return returningUser;
    }

}