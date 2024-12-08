package com.example.timlamgi.Interface;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.timlamgi.Database.DBAccount;
import com.example.timlamgi.Model.Accounts;
import com.example.timlamgi.R;


public class Login extends Fragment {
    EditText uname, pass;
    TextView textView;
    Button login;
    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        uname=viewGroup.findViewById(R.id.username);
        pass=viewGroup.findViewById(R.id.password);
        login=viewGroup.findViewById(R.id.login_btn);
        textView=viewGroup.findViewById(R.id.forgot_pass);
        uname.requestFocus();

        uname.setTranslationX(300);
        uname.setAlpha(0);
        uname.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        pass.setTranslationX(300);
        pass.setAlpha(0);
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        textView.setTranslationX(300);
        textView.setAlpha(0);
        textView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        login.setTranslationX(300);
        login.setAlpha(0);
        login.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uname = view.findViewById(R.id.username);
        pass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = uname.getText().toString();
                String password = pass.getText().toString();
                Log.d("tk", sdt + password);
                DBAccount dbAccount = new DBAccount(getContext());
                Cursor cursor = dbAccount.checkAdminExist("0164");
                if (cursor.getCount() == 0) {
                    new DBAccount(getContext()).themAccount("0164", "123", "147", new DBAccount.onClickListenerRs() {
                        @Override
                        public void success() {
//                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void fail() {
//                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Accounts accounts = new Accounts();
                accounts = dbAccount.layAdmin("0164");
                if (sdt.equals(accounts.getSdt()) && password.equals(accounts.getPassword())){
                    startActivity(new Intent(getContext(),MainActivityHome.class));
                            uname.setText("");
                            pass.setText("");
                }else {
                    new DBAccount(getContext()).checkLogin(sdt, new DBAccount.onClickListener() {
                        @Override
                        public void success(String mk) {
                            if (password.equals(mk)) {
                                Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getContext(), MainActivityAccounts.class));
                                Intent intent = new Intent(getContext(),MainActivityAccounts.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("sdt", sdt);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                uname.setText("");
                                pass.setText("");
                            } else {
                                Toast.makeText(getContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void fail() {
                            Toast.makeText(getContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    });

//                    Toast.makeText(getContext(), "Kiểm tra lại tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlatDialog flatDialog = new FlatDialog(getContext());
                flatDialog.setTitle("Quên mật khẩu")
                        .setSubtitle("Nhập mã của bạn")
                        .setFirstTextFieldHint("Nhập mã")
                        .setFirstButtonText("Hủy")
                        .setSecondButtonText("Xác nhận")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String manv =flatDialog.getFirstTextField();
                                new DBAccount(getContext()).checkManv(manv, new DBAccount.onClickListenerForgot() {
                                    @Override
                                    public void success(String pass) {
                                        flatDialog.dismiss();
                                        FlatDialog flatDialog1 = new FlatDialog(getContext());
                                        flatDialog1.setTitle("Mật khẩu của bạn là: ")
                                                .setSubtitle(pass)
                                                .setFirstButtonText("Đóng")
                                                .withFirstButtonListner(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        flatDialog1.dismiss();
                                                    }
                                                })
                                                .show();
                                    }

                                    @Override
                                    public void fail() {
                                        flatDialog.dismiss();
                                        FlatDialog flatDialog1 = new FlatDialog(getContext());
                                        flatDialog1.setTitle("Mã không tồn tại")
                                                .setSubtitle("Vui lòng kiểm tra lại")
                                                .setFirstButtonText("Đóng")
                                                .withFirstButtonListner(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        flatDialog1.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    }
}