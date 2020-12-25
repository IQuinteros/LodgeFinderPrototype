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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.iquinteros.lodgefinder.MainActivity;
import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.services.UserAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class SettingsFragment extends Fragment {

    User currentUser;

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

        setSaveButton(view);
        setLogoutButton(view);

        return view;
    }

    private void checkLogin(final Context context){
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Cargando usuario actual", true);

        LoginUtil.checkLogin(context, new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                dialog.dismiss();
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
                else{
                    if(user != null){
                        currentUser = user;

                        // Set data
                        EditText nombres = getView().findViewById(R.id.mod_user_nombres);
                        EditText apellidos = getView().findViewById(R.id.mod_user_apellidos);
                        final EditText email = getView().findViewById(R.id.mod_user_email);
                        EditText rut = getView().findViewById(R.id.mod_user_rut);
                        EditText contacto = getView().findViewById(R.id.mod_user_numero);
                        EditText foto = getView().findViewById(R.id.mod_user_foto);
                        CheckBox empresa = getView().findViewById(R.id.mod_user_empresa);

                        nombres.setText(user.getNombres());
                        apellidos.setText(user.getApellidos());
                        email.setText(user.getEmail());
                        rut.setText(Integer.toString(user.getRut()));
                        contacto.setText(Integer.toString(user.getNumeroContacto()));
                        foto.setText(user.getFoto());
                        empresa.setChecked(user.isEmpresa());

                    }else{
                        Toast toast = Toast.makeText(getActivity(), "Usuario inválido", Toast.LENGTH_SHORT);
                        toast.show();
                        LoginUtil.logout(getActivity());
                        Navigation.findNavController(getView()).navigate(R.id.nav_login);
                    }
                }
            }
        });


    }

    private void setSaveButton(View view){
        final View viewFinal = view;

        Button button = (Button) view.findViewById(R.id.mod_user_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentUser == null){ return; }
                // Get all data
                EditText nombres = getView().findViewById(R.id.mod_user_nombres);
                EditText apellidos = getView().findViewById(R.id.mod_user_apellidos);
                final EditText email = getView().findViewById(R.id.mod_user_email);
                final EditText clave = getView().findViewById(R.id.mod_user_contraseña);
                EditText rut = getView().findViewById(R.id.mod_user_rut);
                EditText contacto = getView().findViewById(R.id.mod_user_numero);
                EditText foto = getView().findViewById(R.id.mod_user_foto);
                CheckBox empresa = getView().findViewById(R.id.mod_user_empresa);

                // Validation
                String validation = "";
                if(nombres.getText().toString().isEmpty()){
                    validation += "Debe agregar su nombre\n";
                }
                if(apellidos.getText().toString().isEmpty()){
                    validation += "Debe agregar su apellido\n";
                }
                if(email.getText().toString().isEmpty()){
                    validation += "Debe agregar su email\n";
                }

                if(rut.getText().toString().isEmpty()){
                    validation += "Debe agregar su rut\n";
                }
                if(contacto.getText().toString().isEmpty()){
                    validation += "Debe agregar su número de contacto\n";
                }

                if(!validation.isEmpty()){
                    Toast toast = Toast.makeText(getActivity(), validation.substring(0, validation.length() - 1), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                User newUser = new User();
                newUser.setId(currentUser.getId());
                newUser.setNombres(nombres.getText().toString());
                newUser.setApellidos(apellidos.getText().toString());
                newUser.setEmail(email.getText().toString());
                newUser.setRut(Integer.parseInt(rut.getText().toString()));
                newUser.setNumeroContacto(Integer.parseInt(contacto.getText().toString()));

                String fotoUrl = "1";
                if(!foto.getText().toString().isEmpty()){
                    fotoUrl = foto.getText().toString();
                }
                newUser.setFoto(fotoUrl);
                newUser.setEmpresa(empresa.isChecked());

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Guardando cambios", true);

                UserAPI.userAPI.pushUser(newUser, clave.getText().toString(), true, new GetSuccessResult() {
                    @Override
                    public void onReady(boolean isSuccess) {
                        if(isSuccess){

                            if(!clave.getText().toString().isEmpty()) {
                                LoginUtil.login(getActivity(), email.getText().toString(), clave.getText().toString(), new GetSuccessResult() {
                                    @Override
                                    public void onReady(boolean isSuccess) {
                                        Toast toast;
                                        if (isSuccess) {
                                            toast = Toast.makeText(getActivity(), "Cambios guardados", Toast.LENGTH_LONG);
                                        } else {
                                            toast = Toast.makeText(getActivity(), "No se pudo guardar la sesión", Toast.LENGTH_LONG);
                                        }
                                        clave.setText("");
                                        toast.show();
                                    }
                                });
                            }
                            else{
                                Toast toast;
                                toast = Toast.makeText(getActivity(), "Cambios guardados", Toast.LENGTH_LONG);
                                toast.show();
                            }

                            ((MainActivity)getActivity()).updateLoginUser();
                        }
                        else{
                            // Display error
                            Toast toast = Toast.makeText(getActivity(), "No se pudo guardar los cambios", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        dialog.dismiss();

                    }
                });

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