package com.p2ms.musicbuddy.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.p2ms.musicbuddy.R;
import com.p2ms.musicbuddy.keys.StaticData;
import com.p2ms.musicbuddy.local.LocalSession;
import com.p2ms.musicbuddy.model.User;
import com.p2ms.musicbuddy.ui.notifications.NotificationsViewModel;

import java.util.Map;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    CircularImageView dpID;
    TextInputEditText nameID,emailID,contactID,genderID;
    ImageButton uploadID;
    FloatingActionButton editID;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        dpID=root.findViewById(R.id.profilePicID);
        nameID=root.findViewById(R.id.profile_nameId);
        emailID=root.findViewById(R.id.profile_emailID);
        contactID=root.findViewById(R.id.profile_contactID);
        genderID=root.findViewById(R.id.profile_genderID);
        uploadID=root.findViewById(R.id.upload_buttonID);
        editID=root.findViewById(R.id.edit_buttonID);

        db = FirebaseFirestore.getInstance();
        String currUID= new LocalSession(getContext()).getData(StaticData.ID);
        showData(currUID);
        return root;
    }

    private void showData(String currUID) {
        db.collection("User Details")
                .document(currUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("ProfileFragment","task is successful");
                            DocumentSnapshot snapshot = task.getResult();
                            Map<String,Object> map = snapshot.getData();
                            Log.d("ProfileFragment",String.valueOf(map));

                            User user = new User();
                            user.setName(String.valueOf(map.get(StaticData.NAME)));
                            user.setEmail(String.valueOf(map.get(StaticData.EMAIL)));
                            user.setContact(String.valueOf(map.get(StaticData.CONTACT)));
                            user.setGender(String.valueOf(map.get(StaticData.GENDER)));
                            user.setDP(String.valueOf(map.get(StaticData.DP)));

                            nameID.setText(user.getName());
                            emailID.setText(user.getEmail());
                            contactID.setText(user.getContact());
                            genderID.setText(user.getGender());
                            if(!user.getDP().isEmpty()){
                                Glide.with(getContext()).load(user.getDP()).into(dpID);
                            }
                            progressDialog.dismiss();

                        }else{
                            Log.d("ProfileFragment","task failed because of: "+task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ProfileFragment","fetching data failed because of: "+e.getMessage());
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}