package com.cosmiccoders.spacetraders.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cosmiccoders.spacetraders.entity.Difficulty;
import com.cosmiccoders.spacetraders.entity.Player;
import com.cosmiccoders.spacetraders.entity.Ships.Gnat;
import com.cosmiccoders.spacetraders.entity.Ships.Ship;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.cosmiccoders.spacetraders.R;
import com.cosmiccoders.spacetraders.entity.Skills;
import com.cosmiccoders.spacetraders.viewmodels.EditAddPlayerViewModel;
import com.cosmiccoders.spacetraders.viewmodels.EditShipViewModel;

import org.json.JSONObject;

import java.util.HashMap;

public class PlayerCreation extends AppCompatActivity {

    private EditAddPlayerViewModel playerViewModel;
    private EditShipViewModel shipViewModel;

    private TextView pilotSkills;
    private TextView fighterSkills;
    private TextView traderSkills;
    private TextView engineerSkills;

    private int ps;
    private int fs;
    private int ts;
    private int es;

    private EditText nameField;
    private EditText shipField;

    private Spinner majorSpinner;

    private RequestQueue requestQueue;

    String baseUrl = "http://10.0.2.2:9080/myapi";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        majorSpinner = findViewById(R.id.spinner);
        majorSpinner.setAdapter(new ArrayAdapter<Difficulty>(this,
                android.R.layout.simple_spinner_item, Difficulty.values()));

        shipField = findViewById(R.id.ship_field);
        nameField = findViewById(R.id.name_field);

        pilotSkills = findViewById(R.id.pilot_points);
        traderSkills = findViewById(R.id.trader_points);
        engineerSkills = findViewById(R.id.engineer_points);
        fighterSkills = findViewById(R.id.fighter_points);

        ps = Integer.parseInt(pilotSkills.getText().toString());
        ts = Integer.parseInt(traderSkills.getText().toString());
        fs = Integer.parseInt(fighterSkills.getText().toString());
        es = Integer.parseInt(engineerSkills.getText().toString());

        playerViewModel = ViewModelProviders.of(this).get(EditAddPlayerViewModel.class);
        shipViewModel = ViewModelProviders.of(this).get(EditShipViewModel.class);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void onSubtractPressed(View view) {
        Button change = (Button) findViewById(view.getId());
        int temp = 0;
        switch(view.getId()) {
            case R.id.subtract_pilot:
                temp = R.id.pilot_points;
                break;
            case R.id.subtract_trader:
                temp = R.id.trader_points;
                break;
            case R.id.subtract_fighter:
                temp = R.id.fighter_points;
                break;
            case R.id.subtract_engineer:
                temp = R.id.engineer_points;
        }
        final TextView changeText = (TextView) findViewById(temp);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = ts + ps + fs + es;
                Log.i("MyActivity", "" + total);
                switch(v.getId()) {
                    case R.id.subtract_pilot:
                        if (ps > 0) {
                            ps--;
                            changeText.setText("" + ps);
                            total = ts + ps + fs + es;
                            Log.i("PS", "" + ps);
                            Log.i("MyActivity", "" + total);
                        } else {
                            ps = 0;
                            Log.i("PS", "" + ps);
                            Log.i("MyActivity", "" + total);
                        }
                        break;
                    case R.id.subtract_trader:
                        if(ts > 0) {
                            ts--;
                            changeText.setText("" + ts);
                            total = ts + ps + fs + es;
                            Log.i("TS", "" + ts);
                            Log.i("MyActivity", "" + total);
                        } else {
                            ts = 0;
                            Log.i("TS", "" + ts);
                            Log.i("MyActivity", "" + total);
                        }
                        break;
                    case R.id.subtract_fighter:
                        if(fs > 0) {
                            fs--;
                            changeText.setText("" + fs);
                            total = ts + ps + fs + es;
                            Log.i("FS", "" + fs);
                            Log.i("MyActivity", "" + total);
                        } else {
                            fs = 0;
                            Log.i("FS", "" + fs);
                            Log.i("MyActivity", "" + total);
                        }
                        break;
                    case R.id.subtract_engineer:
                        if(es > 0) {
                            es--;
                            changeText.setText("" + es);
                            total = ts + ps + fs + es;
                            Log.i("ES", "" + es);
                            Log.i("MyActivity", "" + total);
                        } else {
                            es = 0;
                            Log.i("ES", "" + es);
                            Log.i("MyActivity", "" + total);
                        }
                        break;
                }
            }
        });
    }

    public void onAddPressed(View view) {
        Button change = (Button) findViewById(view.getId());
        int temp = 0;
        switch(view.getId()) {
            case R.id.add_pilot:
                temp = R.id.pilot_points;
                break;
            case R.id.add_trader:
                temp = R.id.trader_points;
                break;
            case R.id.add_fighter:
                temp = R.id.fighter_points;
                break;
            case R.id.add_engineer:
                temp = R.id.engineer_points;
        }

        final TextView changeText = (TextView) findViewById(temp);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = ts + ps + fs + es;
                Log.i("MyActivity", "" + total);
                total = ts + ps + fs + es;
                if(total < 16) {
                    switch(v.getId()) {
                        case R.id.add_pilot:
                            ps++;
                            changeText.setText("" + ps);
                            total = ts + ps + fs + es;
                            //Log.i("PS", "" + ps);
                            //Log.i("MyActivity", "" + total);
                            break;
                        case R.id.add_trader:
                            ts++;
                            changeText.setText("" + ts);
                            total = ts + ps + fs + es;
                            //Log.i("TS", "" + ts);
                            //Log.i("MyActivity", "" + total);
                            break;
                        case R.id.add_fighter:
                            fs++;
                            changeText.setText("" + fs);
                            total = ts + ps + fs + es;
                            //Log.i("FS", "" + fs);
                            //Log.i("MyActivity", "" + total);
                            break;
                        case R.id.add_engineer:
                            es++;
                            changeText.setText("" + es);
                            total = ts + ps + fs + es;
                            //Log.i("ES", "" + es);
                            //Log.i("MyActivity", "" + total);
                    }
                }
            }
        });
    }
    public void onCreatePressed(View view) {
        if((ps + ts + es + fs) == 16 ) {
            Player player = new Player();
            Ship ship = new Gnat();
            player.setCurrency(1000);
            player.setDifficulty(Difficulty.EASY);

            String name = nameField.getText().toString();
            String shipName = shipField.getText().toString();
            if (!name.isEmpty() && !shipName.isEmpty()) {
                player.setName(name);
                ship.setName(shipName);
            } else if(name.isEmpty() && !shipName.isEmpty()) {
                ship.setName(shipName);
            } else if(!name.isEmpty() && shipName.isEmpty()) {
                player.setName(name);
                ship.setName("Grancypher");
            } else {
                ship.setName("Grancypher");
            }
            TextView user_id = findViewById(R.id.user_id);
            player.setId(Integer.parseInt(user_id.getText().toString()));
            player.setSkills(Skills.PILOT, Integer.parseInt(pilotSkills.getText().toString()));
            player.setSkills(Skills.FIGHTER, Integer.parseInt(fighterSkills.getText().toString()));
            player.setSkills(Skills.TRADER, Integer.parseInt(traderSkills.getText().toString()));
            player.setSkills(Skills.ENGINEER, Integer.parseInt(engineerSkills.getText().toString()));

            player.setDifficulty((Difficulty) majorSpinner.getSelectedItem());

            shipViewModel.setMainShip(ship);

            playerViewModel.addPlayer(player);
            shipViewModel.addShip(ship);
            shipViewModel.setMainShip(ship);

            addPlayer();
            addShip();
            addCargoHold();
        } else {
            Log.i("MyActivity", "Pleas make sure you've used all your skills!");
        }
    }

    public void onNextPress(View v) {
        Button btn = (Button) findViewById(R.id.next_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerCreation.this, GeneratingUniverse.class));
            }
        });
    }

    public void onExitPressed(View view) {
        Button changeButton = (Button) findViewById(R.id.save_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void addPlayer(){
        this.url = this.baseUrl + "/player";

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", playerViewModel.getPlayer().getId());
        params.put("player_name", playerViewModel.getPlayer().getName());
        params.put("currency", playerViewModel.getPlayer().getCurrency());
        params.put("difficulty", playerViewModel.getPlayer().getDifficulty().getRepresentation());
        params.put("fighter_points", playerViewModel.getPlayer().getSkill(Skills.FIGHTER));
        params.put("trader_points", playerViewModel.getPlayer().getSkill(Skills.TRADER));
        params.put("engineer_points", playerViewModel.getPlayer().getSkill(Skills.ENGINEER));
        params.put("pilot_points", playerViewModel.getPlayer().getSkill(Skills.PILOT));
        params.put("curr_planet", "");
        JSONObject postparams = new JSONObject(params);
        Log.i("Test", postparams.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Volley", "You did it!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley", error.toString());
                    }
                });
        requestQueue.add(jsonObjReq);
    }

    public void addShip(){
        this.url = "http://10.0.2.2:9080/myapi/ship";

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ship_name", shipViewModel.getMainShip().getShipName());
        params.put("ship_type", "Gnat");
        params.put("hull_strength", shipViewModel.getMainShip().getHullStrength());
        params.put("weapon_slots", shipViewModel.getMainShip().getNumOfWeaponSlots());
        params.put("shield_slots", shipViewModel.getMainShip().getNumOfShieldSlots());
        params.put("gadget_slots", shipViewModel.getMainShip().getNumOfGadgetSlots());
        params.put("crew_quarters", shipViewModel.getMainShip().getNumOfCrewQuarters());
        params.put("travel_range", shipViewModel.getMainShip().getMaxTravelRange());
        params.put("escape_pod", "false");
        params.put("fuel", shipViewModel.getMainShip().getFuel());
        params.put("user_id", playerViewModel.getPlayer().getId());

        JSONObject postparams = new JSONObject(params);
        Log.i("Test", postparams.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Volley Ship", "You did it!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Ship", error.toString());
                    }
                });
        requestQueue.add(jsonObjReq);
    }

    public void addCargoHold(){
        this.url = "http://10.0.2.2:9080/myapi/cargohold";

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("curr_size", shipViewModel.getMainShip().getCargoHold().getCurrSize());
        params.put("maxsize", shipViewModel.getMainShip().getCargoHold().getMax());
        params.put("user_id", playerViewModel.getPlayer().getId());

        JSONObject postparams = new JSONObject(params);
        Log.i("Test", postparams.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Volley Ship", "You did it!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Ship", error.toString());
                    }
                });
        requestQueue.add(jsonObjReq);
    }
}