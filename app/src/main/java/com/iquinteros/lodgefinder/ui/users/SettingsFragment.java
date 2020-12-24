package com.iquinteros.lodgefinder.ui.users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.UserAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checkLogin(getActivity());
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        setLogoutButton(view);

        return view;
    }

    private void checkLogin(final Context context){
        LoginUtil.checkLogin(context, new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                if(login == null){

                    new AlertDialog.Builder(context)
                            .setTitle("Debe iniciar sesión")
                            .setMessage("Para acceder a los ajustes, inicie sesión")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Navigation.findNavController(getView()).navigate(R.id.nav_login);

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });


    }

    private void setLogoutButton(View view){
        final View viewFinal = view;

        Button button = (Button) view.findViewById(R.id.logout_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("LOGOUT");

                new AlertDialog.Builder(getView().getContext())
                        .setTitle("Cerrar sesión")
                        .setMessage("Está a un paso de cerrar sesión")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = LoginUtil.logout(getActivity());
                                Toast toast;
                                if(result){
                                     toast = Toast.makeText(getActivity(), "Sesión cerrada", Toast.LENGTH_SHORT);
                                     Navigation.findNavController(getView()).navigate(R.id.nav_search);
                                }
                                else{
                                    toast = Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT);
                                }

                                toast.show();
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}