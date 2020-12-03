package com.iquinteros.lodgefinder.ui.users;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.iquinteros.lodgefinder.services.UserAPI;

public class AddUser extends Fragment {

    private AddUserViewModel mViewModel;

    public static AddUser newInstance() {
        return new AddUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
        // TODO: Use the ViewModel

        ((MainActivity)getActivity()).acceptBtn = getView().findViewById(R.id.accept_btn);


        ((MainActivity)getActivity()).nombres = getView().findViewById(R.id.nombres_user);
        ((MainActivity)getActivity()).apellidos = getView().findViewById(R.id.apellidos_user);
        ((MainActivity)getActivity()).email = getView().findViewById(R.id.email_user);
        ((MainActivity)getActivity()).rut = getView().findViewById(R.id.rut_user);
        ((MainActivity)getActivity()).contacto = getView().findViewById(R.id.contacto_user);
        ((MainActivity)getActivity()).foto = getView().findViewById(R.id.foto_user);
        ((MainActivity)getActivity()).empresa = getView().findViewById(R.id.empresa_user);
    }


}