package com.iquinteros.lodgefinder.ui.lodging;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iquinteros.lodgefinder.MainActivity;
import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Lodging;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.services.LodgingAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class ModifyLodgingFragment extends Fragment {

    public ModifyLodgingFragment() {
        // Required empty public constructor
    }

    public static ModifyLodgingFragment newInstance(String param1, String param2) {
        ModifyLodgingFragment fragment = new ModifyLodgingFragment();
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
        View view = inflater.inflate(R.layout.fragment_modificar_alojamiento, container, false);
        checkCurrentLodging(view);
        setSaveButton(view);
        setDeleteButton(view);
        return view;
    }

    private void setDeleteButton(final View view){
        Button button = (Button) view.findViewById(R.id.mod_lodging_delete_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Eliminar publicación")
                        .setMessage("Está a un paso de eliminar la publicación")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                                        "Eliminando publicación", true);

                                LodgingAPI.lodgingAPI.deleteLodging(((MainActivity) getActivity()).currentLodgingToModify, new GetSuccessResult() {
                                    @Override
                                    public void onReady(boolean isSuccess) {
                                        progressDialog.dismiss();
                                        Toast toast;
                                        if(isSuccess){
                                            toast = Toast.makeText(getActivity(), "Publicación eliminada", Toast.LENGTH_SHORT);
                                            Navigation.findNavController(getView()).navigate(R.id.nav_lodging_view);
                                        }
                                        else{
                                            toast = Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT);
                                        }
                                        toast.show();
                                    }
                                });
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void setSaveButton(final View view){
        Button button = (Button) view.findViewById(R.id.mod_lodging_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText city = (EditText) view.findViewById(R.id.mod_lodging_city);
                EditText description = (EditText) view.findViewById(R.id.mod_lodging_description);
                EditText price = (EditText) view.findViewById(R.id.mod_lodging_price);
                final RadioGroup kind = (RadioGroup) view.findViewById(R.id.mod_lodging_kind);

                // Validation
                String validation = "";
                if(city.getText().toString().isEmpty()){
                    validation += "Debe agregar la ciudad\n";
                }
                if(description.getText().toString().isEmpty()){
                    validation += "Debe agregar la descripción\n";
                }
                if(price.getText().toString().isEmpty()){
                    validation += "Debe agregar un precio\n";
                }
                else if(Integer.parseInt(price.getText().toString()) < 0){
                    validation += "El precio no puede ser negativo\n";
                }

                if(!validation.isEmpty()){
                    Toast toast = Toast.makeText(getActivity(), validation.substring(0, validation.length() - 1), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // Loading
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Guardando cambios", true);

                final Lodging toUpdateLodging = new Lodging();
                toUpdateLodging.setId(((MainActivity)getActivity()).currentLodgingToModify.getId());
                toUpdateLodging.setCity(city.getText().toString());
                toUpdateLodging.setDescription(description.getText().toString());
                toUpdateLodging.setPrice(Integer.parseInt(price.getText().toString()));
                toUpdateLodging.setRating(0);
                toUpdateLodging.setLatCoords(0);
                toUpdateLodging.setLongCoords(0);

                // Get current user
                LoginUtil.checkLogin(getActivity(), new GetLoginResult() {
                    @Override
                    public void onReady(Login login, User user) {
                        if(login == null){ return; }

                        toUpdateLodging.setUserID(user.getId());

                        RadioButton selectedButton = getView().findViewById(kind.getCheckedRadioButtonId());
                        toUpdateLodging.setKind(selectedButton.getText().toString());

                        LodgingAPI.lodgingAPI.pushLodging(toUpdateLodging, true, new GetSuccessResult() {
                            @Override
                            public void onReady(boolean isSuccess) {
                                Toast toast;
                                if(isSuccess){
                                    toast = Toast.makeText(getActivity(), "Alojamiento modificado", Toast.LENGTH_LONG);
                                    Navigation.findNavController(getView()).navigate(R.id.nav_lodging_view);
                                }
                                else{
                                    toast = Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_LONG);
                                }
                                dialog.dismiss();
                                toast.show();
                            }
                        });
                    }
                });

            }
        });
    }

    private void checkCurrentLodging(View view){
        MainActivity mainActivity = (MainActivity)getActivity();
        Lodging lodgingRef = mainActivity.currentLodgingToModify;

        if(lodgingRef == null){
            new AlertDialog.Builder(getActivity())
                .setTitle("Ha ocurrido un error")
                .setMessage("No hay un alojamiento para modificar")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(getView()).navigate(R.id.nav_lodging_view);
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
        else{
            // Set data to inputs
            EditText city = (EditText) view.findViewById(R.id.mod_lodging_city);
            EditText description = (EditText) view.findViewById(R.id.mod_lodging_description);
            EditText price = (EditText) view.findViewById(R.id.mod_lodging_price);
            final RadioGroup kind = (RadioGroup) view.findViewById(R.id.mod_lodging_kind);

            city.setText(lodgingRef.getCity());
            description.setText(lodgingRef.getDescription());
            price.setText(Integer.toString(lodgingRef.getPrice()));

            switch(lodgingRef.getKind()){
                case "Casa":  kind.check(R.id.mod_casa); break;
                case "Departamento":  kind.check(R.id.mod_depto); break;
                case "Cabaña":  kind.check(R.id.mod_cabaña); break;
                case "Habitacion":  kind.check(R.id.mod_habitacion); break;
                case "Sotano":  kind.check(R.id.mod_sotano); break;
                case "Otro":  kind.check(R.id.mod_otro); break;
            }
        }
    }
}