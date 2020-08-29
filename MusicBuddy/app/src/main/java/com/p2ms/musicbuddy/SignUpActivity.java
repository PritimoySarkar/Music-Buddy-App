package com.p2ms.musicbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.p2ms.musicbuddy.keys.StaticData;
import com.p2ms.musicbuddy.model.User;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText nameId,emailId,contactId,passId,passConfId;
    TextView genderText,haveAcc;
    Button signUp;

    private RadioGroup genderGroup;
    private RadioButton genderButton;

    private String name,email,contact,gender,pass,passConf;
    private FirebaseAuth mAuth;

    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameId=findViewById(R.id.nameEditId);
        emailId=findViewById(R.id.emailEditID);
        contactId=findViewById(R.id.contactEditID);
        //gender=findViewById(R.id.nameEditId);
        passId=findViewById(R.id.passwordEditID);
        passConfId=findViewById(R.id.passwordConfEditID);
        genderText=findViewById(R.id.genderTextId);

        signUp=findViewById(R.id.signUpBtnId);
        mAuth=FirebaseAuth.getInstance();

        haveAcc=findViewById(R.id.haveAccId);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
        genderGroup=(RadioGroup) findViewById(R.id.genderId);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=String.valueOf(nameId.getText()).trim();
                email=String.valueOf(emailId.getText()).trim();
                contact=String.valueOf(contactId.getText()).trim();
                pass=String.valueOf(passId.getText()).trim();
                passConf=String.valueOf(passConfId.getText()).trim();
                int selectedGender = genderGroup.getCheckedRadioButtonId(); //Get selected Button ID
                Log.d("SignUpActivity","Checking"+selectedGender);
                genderButton=(RadioButton) findViewById(selectedGender);
                //Toast.makeText(SignUpActivity.this,genderButton.getText(),Toast.LENGTH_LONG).show();

                if(name.isEmpty()) nameId.setError("You have to put a name");
                else if (email.isEmpty()) emailId.setError("Email field can't be blank");
                else if (contact.isEmpty()) contactId.setError("Phone number field can't be blank");
                else if(selectedGender==-1)  genderText.setError("You must select your gender");
                else if (pass.isEmpty()) passId.setError("Password field can't be blank");
                else if (passConf.isEmpty()) passConfId.setError("You have to confirm the password");
                else if (pass.length()<8) passId.setError("Password must be at least 8 character");
                else if (!pass.equals(passConf)){
                    passConfId.setError("Password mismatch");
                    Toast.makeText(SignUpActivity.this,
                            "Password didn't match",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    gender=String.valueOf(genderButton.getText()).trim();
                    Log.d("SignUpActivity","Instance accepted");
                    Log.d("SignUpActivity","data:"+name+email+contact+pass+gender);
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this,
                                        "Signed Up Successfully",
                                        Toast.LENGTH_LONG).show();
                                //Have to add Create profile code here
                                String userId=mAuth.getUid();
                                newUser=new User();
                                newUser.setUserId(userId);
                                newUser.setName(name);
                                newUser.setEmail(email);
                                newUser.setContact(contact);
                                newUser.setGender(gender);
                                createProfile(newUser,"Data Stored successfully");
                            }else{
                                Toast.makeText(SignUpActivity.this,
                                        "Sign Up Failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            private void createProfile(User user, final String data_stored) {
                mAuth.getCurrentUser().sendEmailVerification();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,String> newUser = new HashMap<>();
                newUser.put(StaticData.ID,user.getUserId());
                newUser.put(StaticData.NAME,user.getName());
                newUser.put(StaticData.EMAIL,user.getEmail());
                newUser.put(StaticData.CONTACT,user.getContact());
                newUser.put(StaticData.GENDER,user.getGender());

                db.collection("User Details").document(user.getUserId())
                        .set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this,data_stored,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        SignUpActivity.this.finish();
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this,"Data storing in cloud failed",Toast.LENGTH_LONG).show();
                }
            });
            }
        });
    }
}