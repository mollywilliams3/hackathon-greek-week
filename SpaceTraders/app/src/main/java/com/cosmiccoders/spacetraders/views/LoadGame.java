package com.cosmiccoders.spacetraders.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cosmiccoders.spacetraders.R;
import com.cosmiccoders.spacetraders.entity.Difficulty;
import com.cosmiccoders.spacetraders.entity.Planets.Andromeda;
import com.cosmiccoders.spacetraders.entity.Planets.Baratas;
import com.cosmiccoders.spacetraders.entity.Planets.BlueDwarf;
import com.cosmiccoders.spacetraders.entity.Planets.Cornholio;
import com.cosmiccoders.spacetraders.entity.Planets.Drax;
import com.cosmiccoders.spacetraders.entity.Planets.Kravat;
import com.cosmiccoders.spacetraders.entity.Planets.Omphalos;
import com.cosmiccoders.spacetraders.entity.Planets.PlanetTemp;
import com.cosmiccoders.spacetraders.entity.Planets.RedDwarf;
import com.cosmiccoders.spacetraders.entity.Planets.StartingPlanet;
import com.cosmiccoders.spacetraders.entity.Planets.Titikaka;
import com.cosmiccoders.spacetraders.entity.Player;
import com.cosmiccoders.spacetraders.entity.ShipYard;
import com.cosmiccoders.spacetraders.entity.Ships.Gnat;
import com.cosmiccoders.spacetraders.entity.Ships.Ship;
import com.cosmiccoders.spacetraders.entity.Skills;
import com.cosmiccoders.spacetraders.viewmodels.EditAddPlayerViewModel;
import com.cosmiccoders.spacetraders.viewmodels.EditShipViewModel;
import com.cosmiccoders.spacetraders.viewmodels.GetSetPlanetViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cosmiccoders.spacetraders.viewmodels.ViewAddSolarSystemViewModel;

import java.util.HashMap;
import java.util.Map;


public class LoadGame extends AppCompatActivity {

    private EditShipViewModel shipViewModel;
    private EditAddPlayerViewModel playerViewModel;
    private GetSetPlanetViewModel planetViewModel;
    private ViewAddSolarSystemViewModel solarSystemViewModel;
    private ShipYard shipYard;

    private Player testPlayer;
    private Ship testShip;

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    // This is the API base URL (GitHub API)
    private String baseUrl = "http://10.0.2.2:9080/myapi";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_game);
        playerViewModel = ViewModelProviders.of(this).get(EditAddPlayerViewModel.class);
        planetViewModel = ViewModelProviders.of(this).get(GetSetPlanetViewModel.class);
        shipViewModel = ViewModelProviders.of(this).get(EditShipViewModel.class);
        solarSystemViewModel = ViewModelProviders.of(this).get(ViewAddSolarSystemViewModel.class);
        requestQueue = Volley.newRequestQueue(this);

        testPlayer = new Player();
        testShip = new Gnat();

        solarSystemViewModel.setPlanetSS(new StartingPlanet());
        solarSystemViewModel.setPlanetSS(new Andromeda());
        solarSystemViewModel.setPlanetSS(new Baratas());
        solarSystemViewModel.setPlanetSS(new Cornholio());
        solarSystemViewModel.setPlanetSS(new Drax());
        solarSystemViewModel.setPlanetSS(new Kravat());
        solarSystemViewModel.setPlanetSS(new Omphalos());
        solarSystemViewModel.setPlanetSS(new Titikaka());
        solarSystemViewModel.setPlanetSS(new RedDwarf());
        solarSystemViewModel.setPlanetSS(new BlueDwarf());

        /*for (MapPage.Entry<String, PlanetTemp> entry : solarSystemViewModel.getPlanetMap().entrySet()) {
            Log.i("Planet name", entry.getKey());
            Log.i("Test", entry.getValue().toString());
        }*/
    }

    public void onLoadClicked(View v) {
        Button changeButton = (Button) findViewById(R.id.load_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id = findViewById(R.id.id_field);
                if(!id.getText().toString().matches(".*[a-zA-Z]+.*")) {
                    Log.i("Test", id.getText().toString());
                    int user_id = Integer.parseInt(id.getText().toString());
                    loadPlayer(user_id);
                    loadShip(user_id);
                    loadCargoHold(user_id);
                    loadItem(user_id);
                    testPlayer.setId(user_id);
                    playerViewModel.addPlayer(testPlayer);
                    shipViewModel.setMainShip(testShip);
                    Log.i("Test Load P", playerViewModel.toString());
                    Log.i("Test Load Ship", shipViewModel.getMainShip().getCargoHold().toString());

                    startActivity(new Intent(LoadGame.this, ShipHome.class));

                } else {
                    Log.i("Error", "The user id you have entered is not valid!");
                }

            }
        });
    }

    public Boolean isNumeric(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if(Character.isLetter(c)) {
                Log.i("Numeric", "There is a letter");
                return false;
            }
        }

        return true;
    }

    public void loadPlayer(int user_id) {
        this.url = this.baseUrl + "/player/id/" + user_id;

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String player_name = jsonObj.get("player_name").toString();
                                    int currency = jsonObj.getInt("currency");
                                    String difficulty = jsonObj.get("difficulty").toString();
                                    int fp = jsonObj.getInt("fighter_points");
                                    int tp = jsonObj.getInt("trader_points");
                                    int ep = jsonObj.getInt("engineer_points");
                                    int pp = jsonObj.getInt("pilot_points");
                                    String currPlanet = jsonObj.get("curr_planet").toString();

                                    Difficulty d;
                                    switch(difficulty){
                                        case "easy":
                                            d = Difficulty.EASY;
                                            break;
                                        case "beginner":
                                            d = Difficulty.BEGINNER;
                                            break;
                                        case "hard":
                                            d = Difficulty.HARD;
                                        default:
                                            d = Difficulty.NORMAL;
                                    }
                                    testPlayer = new Player(player_name, pp, fp, tp, ep, currency, d);
                                    playerViewModel.updatePlayer(testPlayer);
                                    planetViewModel.setPlanet(solarSystemViewModel.getPlanet(currPlanet));
                                    Log.i("Test", playerViewModel.toString());
                                    Log.i("Test", planetViewModel.getPlanet().toString());
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                                Log.i("Testing", i+"");
                            }
                        } else {
                            Log.i("Test rest api", "No repos founds");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Test rest api", "The ID you are trying to find does not exist!");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    public void loadShip(int user_id){
        this.url = baseUrl + "/ship/id/" + user_id;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String shipname = jsonObj.get("ship_name").toString();
                                    int fuel = jsonObj.getInt("fuel");
                                    testShip.setName(shipname);
                                    testShip.setFuel(fuel);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                                Log.i("Testing", i+"");
                            }
                        } else {
                            // The user didn't have any repos.
                            //setRepoListText("No repos found.");
                            Log.i("Test rest api", "No repos founds");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        //setRepoListText("The ID you are trying to find does not exist!");
                        Log.i("Test rest api", "The ID you are trying to find does not exist!");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }

    public void loadCargoHold(int user_id) {
        this.url = baseUrl + "/cargohold/id/" + user_id;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    int max = jsonObj.getInt("maxsize");
                                    int curr = jsonObj.getInt("curr_size");
                                    testShip.getCargoHold().setCurrSize(curr);
                                    //shipViewModel.getMainShip().getCargoHold().setCurrSize(curr);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                                Log.i("Testing", i+"");
                            }
                        } else {
                            // The user didn't have any repos.
                            //setRepoListText("No repos found.");
                            Log.i("Test rest api", "No repos founds");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        //setRepoListText("The ID you are trying to find does not exist!");
                        Log.i("Test rest api", "The ID you are trying to find does not exist!");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }

    public void loadItem(int user_id) {
        this.url = baseUrl + "/items/id/" + user_id;
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Map<String, Integer> temp = new HashMap<>();

                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String item_name = jsonObj.get("item_name").toString();
                                    int amount = jsonObj.getInt("curr_amount");
                                    temp.put(item_name, amount);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                                Log.i("Testing", i+"");
                            }
                            testShip.getCargoHold().setInventory(temp);
                        } else {
                            // The user didn't have any repos.
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }
}
