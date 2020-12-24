package com.iquinteros.lodgefinder.ui.users;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iquinteros.lodgefinder.MainActivity;
import com.iquinteros.lodgefinder.R;

public class DeleteUserFragment extends Fragment {

    private DeleteUserViewModel mViewModel;

    public static DeleteUserFragment newInstance() {
        return new DeleteUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.delete_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        // TODO: Use the ViewModel

        ((MainActivity)getActivity()).deleteBtn = getView().findViewById(R.id.delete_btn);


        ((MainActivity)getActivity()).delete_rut = getView().findViewById(R.id.delete_text);
    }

}