package com.iquinteros.lodgefinder.ui.users;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iquinteros.lodgefinder.R;

public class ViewUser extends Fragment {

    private ViewUserViewModel mViewModel;

    public static ViewUser newInstance() {
        return new ViewUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewUserViewModel.class);
        // TODO: Use the ViewModel

        loadUsersList();
    }

    public void loadUsersList(){
        /*
        UserAPI userApi = ((MainActivity)getActivity()).userApi;

        ((MainActivity)getActivity()).listView = getView().findViewById(R.id.view_users);

        // Load from API

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(getContext(), android.R.layout.simple_list_item_1, userApi.getUsers());
        Toast toast = Toast.makeText(getContext(), "Usuarios cargados", Toast.LENGTH_SHORT);
        toast.show();

        ((MainActivity)getActivity()).listView.setAdapter(adapter);

        ((MainActivity)getActivity()).listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(((User)((MainActivity)getActivity()).listView.getItemAtPosition(i)) == null){
                    return;
                }

                User selectedUser = ((User)((MainActivity)getActivity()).listView.getItemAtPosition(i));

                new AlertDialog.Builder(view.getContext())
                        .setTitle(Integer.toString(selectedUser.getRut()))
                        .setMessage(selectedUser.toComplexString())

                        .setPositiveButton(android.R.string.yes, null)

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });
        */
    }

}