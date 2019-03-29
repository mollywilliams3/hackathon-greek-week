package com.cosmiccoders.spacetraders.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.RadioGroup;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cosmiccoders.spacetraders.R;
import com.cosmiccoders.spacetraders.viewmodels.EditAddPlayerViewModel;
import com.cosmiccoders.spacetraders.viewmodels.EditShipViewModel;
import com.cosmiccoders.spacetraders.viewmodels.GetSetPlanetViewModel;

public class ShipHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private EditShipViewModel shipViewModel;
    private EditAddPlayerViewModel playerViewModel;
    private GetSetPlanetViewModel planetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_home);
        playerViewModel = ViewModelProviders.of(this).get(EditAddPlayerViewModel.class);
        planetViewModel = ViewModelProviders.of(this).get(GetSetPlanetViewModel.class);
        shipViewModel = ViewModelProviders.of(this).get(EditShipViewModel.class);

        Button btn = (Button) findViewById(R.id.go_places);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ShipHome.this, v);
                popup.setOnMenuItemClickListener(ShipHome.this);
                popup.inflate(R.menu.go_places_menu);
                popup.show();
            }
        });
    }


    public void onMapPressed(View view) {
        Button btn2 = (Button) findViewById(R.id.view_map_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShipHome.this, Map.class));
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.go_market:
                Log.i("Testing: ", "Yay!");
                startActivity(new Intent(ShipHome.this, MarketPlace.class));
                return true;
            case R.id.go_ship:
                //Log.i("Testing", planetViewModel.getPlanet().toString());
                return true;
            case R.id.go_bank:
                // do your code
                return true;
            case R.id.go_police:
                // do your code
                return true;
            default:
                return false;
        }
    }

    public void viewStats(View view) {
        Button changeButton = (Button) findViewById(R.id.view_stats_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Player", playerViewModel.toString());
                Log.i("Planet", planetViewModel.getPlanet().toString());
                Log.i("Ship", shipViewModel.getMainShip().getCargoHold().toString());
                Log.i("Market", planetViewModel.getPlanet().getMarket().toString());
            }
        });
    }

}
