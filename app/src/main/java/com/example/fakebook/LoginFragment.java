package com.example.fakebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;


public class LoginFragment extends Fragment implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    View view;
    TextView dangkimoi, quenmatkhau;
    Button btndangnhap;
    EditText edtemail, edtpassword;
    FirebaseAuth firebaseAuth;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        navController = Navigation.findNavController(view);

        firebaseAuth = FirebaseAuth.getInstance();

        initWidget();

        dangkimoi.setOnClickListener(this);
        btndangnhap.setOnClickListener(this);

        quenmatkhau.setOnClickListener(this);

    }
    public void initWidget()
    {
        dangkimoi = view.findViewById(R.id.txtdangkimoi);
        btndangnhap = view.findViewById(R.id.btnlogin);
        edtemail =  view.findViewById(R.id.edtloginemail);
        edtpassword= view.findViewById(R.id.edtloginpassword);
        quenmatkhau= view.findViewById(R.id.txtquenmatkhau);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.txtdangkimoi:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;
            case R.id.btnlogin:
                dangnhap();
                break;
            case R.id.txtquenmatkhau:
                navController.navigate(R.id.action_loginFragment_to_forgetPassFragment);
                break;

        }
    }
    public void dangnhap() {
        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();
        if ( !email.isEmpty() && !password.isEmpty() ){

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Đăng nhập thành công!", LENGTH_SHORT).show();
                        // zo trang chu
                        Intent mainIntent=new Intent(getContext(),MainActivity.class);
                        getActivity().startActivity(mainIntent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getActivity(),"Bạn chưa nhập đầy đủ!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

    }
}