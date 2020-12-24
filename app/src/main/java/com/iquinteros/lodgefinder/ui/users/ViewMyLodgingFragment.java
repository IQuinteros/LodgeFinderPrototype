package com.iquinteros.lodgefinder.ui.users;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Lodging;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetLodgingsResult;
import com.iquinteros.lodgefinder.services.LodgingAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

import java.util.ArrayList;

public class ViewMyLodgingFragment extends Fragment {

    private ViewMyLodgingViewModel mViewModel;

    public static ViewMyLodgingFragment newInstance() {
        return new ViewMyLodgingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loadMyLodgingsList();
        return inflater.inflate(R.layout.view_my_lodging_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewMyLodgingViewModel.class);
        // TODO: Use the ViewModel
    }

    public void loadMyLodgingsList(){

        // Get login user
        LoginUtil.checkLogin(getActivity(), new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                if(user != null) {
                    // Load from API
                    LodgingAPI.lodgingAPI.getLodgingsOfUser(user.getId(), new GetLodgingsResult() {
                        @Override
                        public void onReady(ArrayList<Lodging> foundLodgings) {
                            ArrayAdapter<Lodging> adapter = new ArrayAdapter<Lodging>(getActivity(), android.R.layout.simple_list_item_1, foundLodgings);

                            final ListView lodgingList = (ListView) getView().findViewById(R.id.view_user_lodgings);
                            lodgingList.setAdapter(adapter);

                            lodgingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    if ((lodgingList.getItemAtPosition(i)) == null) {
                                        return;
                                    }

                                    Lodging selectedLodging = ((Lodging) lodgingList.getItemAtPosition(i));

                                    // TODO: Open navigation with lodging reference

                                    new AlertDialog.Builder(view.getContext())
                                            .setTitle(selectedLodging.getCity())
                                            .setMessage(selectedLodging.toComplexString())

                                            .setPositiveButton(android.R.string.yes, null)

                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .show();

                                }
                            });

                            if (foundLodgings.size() <= 0) {
                                Toast toast = Toast.makeText(getActivity(), "No se han encontrado publicaciones", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                }
                else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Debe iniciar sesión")
                            .setMessage("Para buscar alojamientos, inicie sesión")

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


}