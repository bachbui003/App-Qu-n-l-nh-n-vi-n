package com.example.timlamgi.Interface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timlamgi.Database.DBAccount;
import com.example.timlamgi.R;

public class SignUp extends Fragment {

    EditText edtName, edtPassword, edtPhone;
    Button btnSignUp;

    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName = view.findViewById(R.id.username);
        edtPassword =view.findViewById(R.id.password);
        edtPhone = view.findViewById(R.id.phoneNo);
        btnSignUp = view.findViewById(R.id.sigup_btn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name = edtName.getText().toString();
            String password = edtPassword.getText().toString();
            String phone = edtPhone.getText().toString();

            new DBAccount(getContext()).themAccount(name, password, phone, new DBAccount.onClickListenerRs() {
                @Override
                public void success() {
                    Toast.makeText(getContext(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPassword.setText("");
                    edtPhone.setText("");
                }
                @Override
                public void fail() {
                    Toast.makeText(getContext(), "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
    }
}