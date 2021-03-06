package com.cosmiccoders.spacetraders.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cosmiccoders.spacetraders.model.Model;
import com.cosmiccoders.spacetraders.model.ShipInteractor;
import com.cosmiccoders.spacetraders.entity.Ships.Ship;

public class EditShipViewModel extends AndroidViewModel {

    private ShipInteractor interactor;

    public EditShipViewModel(@NonNull Application application) {
        super(application);
        interactor = Model.getInstance().getShipInteractors();
    }

    public void updateShip(Ship ship) { interactor.updateShip(ship);}

    public void addShip(Ship ship) {
        interactor.addShip(ship);
    }

    public void setMainShip(Ship ship) { interactor.setMainShip(ship);}

    public Ship getMainShip() { return interactor.getMainShip();}
}