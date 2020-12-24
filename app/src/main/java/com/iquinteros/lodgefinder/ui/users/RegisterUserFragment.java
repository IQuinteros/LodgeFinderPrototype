package com.iquinteros.lodgefinder.ui.users;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.services.UserAPI;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class RegisterUserFragment extends Fragment {

    private RegisterUserViewModel mViewModel;

    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_user_fragment, container, false);

        setRegisterButton(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterUserViewModel.class);
        // TODO: Use the ViewModel

    }

    private void setRegisterButton(View view){
        final View viewFinal = view;

        Button button = (Button) view.findViewById(R.id.register_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("REGISTER");

                // Get all data
                EditText nombres = getView().findViewById(R.id.nombres_user);
                EditText apellidos = getView().findViewById(R.id.apellidos_user);
                final EditText email = getView().findViewById(R.id.email_user);
                final EditText clave = getView().findViewById(R.id.pass_user);
                EditText rut = getView().findViewById(R.id.rut_user);
                EditText contacto = getView().findViewById(R.id.contacto_user);
                EditText foto = getView().findViewById(R.id.foto_user);
                CheckBox empresa = getView().findViewById(R.id.empresa_user);

                // TODO: Validation

                User newUser = new User();
                newUser.setNombres(nombres.getText().toString());
                newUser.setApellidos(apellidos.getText().toString());
                newUser.setEmail(email.getText().toString());
                newUser.setRut(Integer.parseInt(rut.getText().toString()));
                newUser.setNumeroContacto(Integer.parseInt(contacto.getText().toString()));
                newUser.setFoto(foto.getText().toString());
                newUser.setEmpresa(empresa.isChecked());

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Registrando", true);

                UserAPI.userAPI.pushUser(newUser, clave.getText().toString(), false, new GetSuccessResult() {
                    @Override
                    public void onReady(boolean isSuccess) {
                        if(isSuccess){
                            LoginUtil.login(getActivity(), email.getText().toString(), clave.getText().toString(), new GetSuccessResult() {
                                @Override
                                public void onReady(boolean isSuccess) {
                                    Toast toast = Toast.makeText(getActivity(), "No se pudo registrar", Toast.LENGTH_LONG);
                                    if(isSuccess) {
                                        toast = Toast.makeText(getActivity(), "Usuario registrado", Toast.LENGTH_LONG);
                                    }
                                    else{
                                        toast = Toast.makeText(getActivity(), "No se pudo iniciar sesión", Toast.LENGTH_LONG);
                                    }
                                    Navigation.findNavController(getView()).navigate(R.id.nav_search);
                                    toast.show();
                                }
                            });
                        }
                        else{
                            // Display error
                            Toast toast = Toast.makeText(getActivity(), "No se pudo registrar", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        dialog.dismiss();

                    }
                });

            }
        });
    }


}