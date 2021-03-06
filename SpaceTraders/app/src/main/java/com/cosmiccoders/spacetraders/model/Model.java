package com.cosmiccoders.spacetraders.model;

import com.cosmiccoders.spacetraders.entity.SolarSystem;

import java.util.HashMap;
import java.util.Map;

public class Model {

    /** the data repository */
    private Repository myRepository;

    private Map<String, Object> interactorMap;

    /** Singleton Pattern Code
     *  this allows us to get access to this class
     *  anywhere, which will allow our View models to access
     *  the "back end"  more easily
     */
    private static Model instance = new Model();

    public static Model getInstance() { return instance; }

    /**
     * Make a new Model instance
     */
    private Model() {
        myRepository = new Repository();
        interactorMap = new HashMap<>();
        registerInteractors();
    }

    private void registerInteractors() {
        interactorMap.put("Player", new PlayerInteractor(myRepository));
        interactorMap.put("Ship", new ShipInteractor(myRepository));
        interactorMap.put("Planet", new PlanetInteractor(myRepository));
        interactorMap.put("SolarSystem", new SolarSystemInteractor(myRepository));
    }

    public PlanetInteractor getPlanetInteractor() {
        return (PlanetInteractor) interactorMap.get("Planet");
    }
    public PlayerInteractor getPlayerInteractors() {
        return (PlayerInteractor) interactorMap.get("Player");
    }

    public ShipInteractor getShipInteractors() {
        return (ShipInteractor) interactorMap.get("Ship");
    }

    public SolarSystemInteractor getSolarSystemInteractors() {
        return (SolarSystemInteractor) interactorMap.get("SolarSystem");
    }
}
