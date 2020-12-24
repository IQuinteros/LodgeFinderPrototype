package com.iquinteros.lodgefinder.ui.lodging;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iquinteros.lodgefinder.R;
import com.iquinteros.lodgefinder.models.Lodging;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.services.GetLodgingsResult;
import com.iquinteros.lodgefinder.services.LodgingAPI;
import com.iquinteros.lodgefinder.utils.LoginUtil;

import java.util.ArrayList;

public class SearchLodgingFragment extends Fragment {

    private SearchLodgingViewModel mViewModel;

    public static SearchLodgingFragment newInstance() {
        return new SearchLodgingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_lodging_fragment, container, false);

        onSearchKeyDown(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchLodgingViewModel.class);
        // TODO: Use the ViewModel

        loadLodgings();
    }

    private void onSearchKeyDown(View view){
        final View viewFinal = view;

        EditText search = (EditText) view.findViewById(R.id.search_lodging_text);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                loadLodgings();
            }
        });

    }

    public void loadLodgings(){

        EditText search = (EditText) getView().findViewById(R.id.search_lodging_text);

        // Load from API
        LodgingAPI.lodgingAPI.getLodgingsBySearch(search.getText().toString(), new GetLodgingsResult() {
            @Override
            public void onReady(ArrayList<Lodging> foundLodgings) {
                ArrayAdapter<Lodging> adapter = new ArrayAdapter<Lodging>(getActivity(), android.R.layout.simple_list_item_1, foundLodgings);

                final ListView lodgingList = (ListView) getView().findViewById(R.id.search_lodging_list);
                lodgingList.setAdapter(adapter);

                lodgingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if((lodgingList.getItemAtPosition(i)) == null){
                            return;
                        }

                        Lodging selectedLodging = ((Lodging)lodgingList.getItemAtPosition(i));

                        new AlertDialog.Builder(view.getContext())
                                .setTitle(selectedLodging.getCity())
                                .setMessage(selectedLodging.toComplexString())

                                .setPositiveButton(android.R.string.yes, null)

                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();

                    }
                });

                if(foundLodgings.size() <= 0){
                    Toast toast = Toast.makeText(getActivity(), "No se han encontrado alojamientos", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



    }

}