package com.iquinteros.lodgefinder.ui.lodging;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class SearchLodgingFragment extends Fragment {

    private SearchLodgingViewModel mViewModel;

    public static SearchLodgingFragment newInstance() {
        return new SearchLodgingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Login login = LoginUtil.checkLogin(getContext());

        if(login == null){
            Toast.makeText(getContext(), "Primero inicia sesión", Toast.LENGTH_LONG);
            System.out.println("DEBE INICIAR");
        }
        else{
            Toast.makeText(getContext(), "Sesión iniciada", Toast.LENGTH_SHORT);
            System.out.println("INICIADO");
        }
        return inflater.inflate(R.layout.search_lodging_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchLodgingViewModel.class);
        // TODO: Use the ViewModel

    }

}