package com.iquinteros.lodgefinder.ui.users;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class LoginFragment extends Fragment {



    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkLogin(getActivity());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Set buttons behavior
        setLoginButton(view);
        setGoToRegisterButton(view);

        return view;
    }

    private void setLoginButton(View view){
        final View viewFinal = view;

        Button button = (Button) view.findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("LOGIN");
                EditText emailLogin = (EditText) viewFinal.findViewById(R.id.email_login);
                EditText passLogin = (EditText) viewFinal.findViewById(R.id.password_login);
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Cargando", true);
                LoginUtil.login(getActivity(), emailLogin.getText().toString(), passLogin.getText().toString(), new GetSuccessResult() {
                    @Override
                    public void onReady(boolean isSuccess) {
                        Toast toast;
                        if(isSuccess){
                            toast = Toast.makeText(getActivity(), "Sesión iniciada", Toast.LENGTH_LONG);
                            Navigation.findNavController(getView()).navigate(R.id.nav_search);
                        }
                        else{
                            // Display error
                            toast = Toast.makeText(getActivity(), "No se pudo iniciar sesión con estos datos", Toast.LENGTH_LONG);
                        }

                        toast.show();
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    private void setGoToRegisterButton(View view){
        Button button = (Button) view.findViewById(R.id.go_to_register_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Go to register
                System.out.println("GO TO REGISTER");
                Navigation.findNavController(getView()).navigate(R.id.nav_addUser);
            }
        });
    }

    private void checkLogin(final Context context){
        LoginUtil.checkLogin(context, new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                if(login != null){

                    new AlertDialog.Builder(context)
                            .setTitle("Ya inició sesión")
                            .setMessage("No es necesario volver a iniciar sesión")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Navigation.findNavController(getView()).navigate(R.id.nav_search);

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }
}