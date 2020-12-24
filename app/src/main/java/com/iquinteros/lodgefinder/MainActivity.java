package com.iquinteros.lodgefinder;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.LoginAPI;
import com.iquinteros.lodgefinder.utils.GetLoginResult;
import com.iquinteros.lodgefinder.utils.LoginUtil;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    /* ADD */

    // Inputs in Add
    public EditText nombres;
    public EditText apellidos;
    public EditText email;
    public EditText rut;
    public EditText contacto;
    public EditText foto;
    public CheckBox empresa;

    // Buttons in Add
    public Button acceptBtn;

    /* VIEW */
    public ListView listView;

    /* DELETE */
    public EditText delete_rut;

    // Buttons in Delete
    public Button deleteBtn;

    // API
    public LoginAPI loginAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Desarrollado por Ignacio Quinteros y Joaquín Ruíz", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_addAl, R.id.nav_view, R.id.nav_search, R.id.nav_login, R.id.nav_modU)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        loginAPI = new LoginAPI(getBaseContext());

        updateLoginUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateLoginUser(){
        LoginUtil.checkLogin(this, new GetLoginResult() {
            @Override
            public void onReady(Login login, User user) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View hView =  navigationView.getHeaderView(0);

                TextView headerUserName = (TextView)hView.findViewById(R.id.header_user_name);
                headerUserName.setText(user != null? user.getNombres() + " " + user.getApellidos() : "Inicie sesión");

                TextView headerUserEmail = (TextView)hView.findViewById(R.id.header_user_email);
                headerUserEmail.setText(user != null? user.getEmail() : "Para acceder a algunas funciones");
            }
        });


    }

    public void addUser(View view){
        /*

        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String emailText = email.getText().toString();
        String rutText = rut.getText().toString();
        String contactoText = contacto.getText().toString();
        String fotoText = foto.getText().toString();
        boolean empresaBool = empresa.isChecked();

        // Check inputs

        String incomplete = "";

        if(nombresText.isEmpty()){
            incomplete += "El campo nombre está incompleto\n";
        }
        if(apellidosText.isEmpty()){
            incomplete += "El campo apellido está incompleto\n";
        }
        if(emailText.isEmpty()){
            incomplete += "El campo email está incompleto\n";
        }
        if(rutText.isEmpty()){
            incomplete += "El campo rut está incompleto\n";
        }
        if(contactoText.isEmpty()){
            incomplete += "El campo contacto está incompleto\n";
        }

        if(!incomplete.isEmpty()){
            Toast toast = Toast.makeText(this, incomplete, Toast.LENGTH_LONG);
            toast.show();

            return;
        }

        // Check foto ID

        int fotoID = 1;

        if(!fotoText.isEmpty()){
            fotoID = Integer.parseInt(fotoText);
        }

        // Create user from model
        final User user = new User(
                0,
                nombresText,
                apellidosText,
                emailText,
                Integer.parseInt(rutText),
                Integer.parseInt(contactoText),
                fotoID,
                empresaBool
        );

        final Context thisContext = this;

        if(userApi.findUserByRut(user.getRut()) != null){

            new AlertDialog.Builder(view.getContext())
                .setTitle("¿Modificar usuario?")
                .setMessage("Ya hay un usuario con este rut. ¿Desea modificarlo con estos nuevos datos?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userApi.modifyUser(user);

                        Toast toast = Toast.makeText(thisContext, "Modificado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        }
        else{
            userApi.insert(user);

            Toast toast = Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT);
            toast.show();
        }
        */
    }

    public void deleteUser(View view){
        /*
        int rutText = Integer.parseInt(delete_rut.getText().toString());

        Toast toast = Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT);
        if(userApi.deleteUserByRut(rutText)){
            toast = Toast.makeText(this, "Eliminado satisfactoriamente", Toast.LENGTH_SHORT);
        }else{
            toast = Toast.makeText(this, "Rut no encontrado", Toast.LENGTH_SHORT);
        }
        toast.show();
        */
    }

}