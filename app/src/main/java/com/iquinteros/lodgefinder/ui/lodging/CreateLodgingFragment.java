package com.iquinteros.lodgefinder.ui.lodging;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Lodging;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.services.LodgingAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

public class CreateLodgingFragment extends Fragment {

    public CreateLodgingFragment() {
        // Required empty public constructor
    }

    public static CreateLodgingFragment newInstance() {
        CreateLodgingFragment fragment = new CreateLodgingFragment();
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
        View view = inflater.inflate(R.layout.fragment_crear_alojamiento, container, false);

        setCreateLodgingButton(view);
        kindDefault(view);

        return view;
    }

    private void kindDefault(View view){
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.add_lodging_kind);
        radioGroup.check(R.id.Casa);
    }

    private void setCreateLodgingButton(final View view){
        Button button = (Button) view.findViewById(R.id.lodging_add_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText city = (EditText) getView().findViewById(R.id.add_lodging_city);
                EditText description = (EditText) getView().findViewById(R.id.add_lodging_description);
                EditText price = (EditText) getView().findViewById(R.id.add_lodging_price);
                final RadioGroup kind = (RadioGroup) getView().findViewById(R.id.add_lodging_kind);

                // TODO: Validation

                // Loading
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                        "Cargando", true);

                final Lodging newLodging = new Lodging();
                newLodging.setCity(city.getText().toString());
                newLodging.setDescription(description.getText().toString());
                newLodging.setPrice(Integer.parseInt(price.getText().toString()));
                newLodging.setRating(0);
                newLodging.setLatCoords(0);
                newLodging.setLongCoords(0);

                // Get current user
                LoginUtil.checkLogin(getActivity(), new GetLoginResult() {
                    @Override
                    public void onReady(Login login, User user) {
                        if(login == null){ return; }

                        newLodging.setUserID(user.getId());

                        RadioButton selectedButton = getView().findViewById(kind.getCheckedRadioButtonId());
                        newLodging.setKind(selectedButton.getText().toString());


                        LodgingAPI.lodgingAPI.pushLodging(newLodging, false, new GetSuccessResult() {
                            @Override
                            public void onReady(boolean isSuccess) {
                                Toast toast;
                                if(isSuccess){
                                    toast = Toast.makeText(getActivity(), "Alojamiento añadido", Toast.LENGTH_LONG);
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

    private void checkLogin(final Context context){
        LoginUtil.checkLogin(context, new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                if(login == null){

                    new AlertDialog.Builder(context)
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