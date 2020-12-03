package com.iquinteros.lodgefinder;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.UserAPI;

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

    // API
    public UserAPI userApi;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_add, R.id.nav_view, R.id.nav_modify, R.id.nav_delete)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        userApi = new UserAPI(getBaseContext());
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

    public void addUser(View view){

        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String emailText = email.getText().toString();
        String rutText = rut.getText().toString();
        String contactoText = contacto.getText().toString();
        String fotoText = foto.getText().toString();
        boolean empresaBool = empresa.isChecked();

        // Create user from model
        User user = new User(
                0,
                nombresText,
                apellidosText,
                emailText,
                Integer.parseInt(rutText),
                Integer.parseInt(contactoText),
                Integer.parseInt(fotoText),
                empresaBool
        );

        userApi.insert(user);
        Toast toast = Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT);
        toast.show();

    }

}