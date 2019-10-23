package com.codehouse.vk;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class menuPrincipal extends AppCompatActivity {

    BottomNavigationView bottomNav;
    NavigationView navigationView;

    Toolbar mainToolbar;
    NavigationView navView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNav.setSelectedItemId(R.id.bllaves);


    }



    //Codigo para crear un Listener para escuchar eventos de los botones de BottomNavigationView
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id= menuItem.getItemId();
            Fragment fragment= null;

            switch (id){
                case R.id.bdispositivos:
                    fragment = new dispositivos();
                    break;

                case R.id.bllaves:
                    fragment = new llaves();
                    break;

                case R.id.bregistros:
                    fragment = new registros();
                    break;

                case R.id.bperfil:
                    fragment = new perfil();
                    break;
            }

            if(fragment!=null){
                FragmentManager fragmentManager= getSupportFragmentManager();
                FragmentTransaction transaction= fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor_fragments,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            return true;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            System.out.println("BACK KEY PRESSED " + getSupportFragmentManager().getBackStackEntryCount());

            if(getSupportFragmentManager().findFragmentById(R.id.contenedor_fragments) instanceof dispositivos){
                System.out.println("FINALIZANDO ACTIVIDAD");
                finish();
            }

            //Si no queda ningun elemento en el Stack del Manager, entonces volvemos al home
            else if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                bottomNav.setSelectedItemId(R.id.bllaves);
            }

            else{
                System.out.println("POPING OTHERS");
                getSupportFragmentManager().popBackStackImmediate();

                if(getSupportFragmentManager().findFragmentById(R.id.contenedor_fragments) instanceof dispositivos){

                    bottomNav.setSelectedItemId(R.id.bdispositivos);
                }
                else if(getSupportFragmentManager().findFragmentById(R.id.contenedor_fragments) instanceof llaves){
                    bottomNav.setSelectedItemId(R.id.bllaves);
                }
                else if(getSupportFragmentManager().findFragmentById(R.id.contenedor_fragments) instanceof perfil){
                    bottomNav.setSelectedItemId(R.id.bperfil);
                }
                else{
                    bottomNav.setSelectedItemId(R.id.bregistros);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);

    }



}
