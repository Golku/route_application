package com.example.jason.route_application.features.container.mapFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.MarkerInfo;
import com.example.jason.route_application.data.pojos.api.DriveRequest;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class MapPresenter implements MvpMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpMap.View view;

    private LatLng userLocation;

    private List<Address> addressList;

    private List<Marker> routeOrder;

    private Marker previousSelectedMarker;

    MapPresenter(MvpMap.View view, List<Address> addressList, List<String> routeOrder) {
        this.view = view;
        this.addressList = addressList;
        this.routeOrder = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMarkers() {
        view.addMarkersToMap(addressList);
    }

    @Override
    public void multipleMarkersDeselected(int markerPosition) {

        String markerDestination = routeOrder.get(markerPosition).getTitle();

        for(int i = markerPosition; i<routeOrder.size(); i++){
            Marker marker = routeOrder.get(i);
            MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

            markerInfo.setSelected(false);

            if(markerInfo.isBusiness()){
                markerInfo.setIconType("business");
            }else{
                markerInfo.setIconType("private");
            }

            view.changeMarkerIcon(marker);
        }

        routeOrder.subList(markerPosition, routeOrder.size()).clear();

        if (routeOrder.size() > 0) {
            previousSelectedMarker = routeOrder.get(routeOrder.size() -1);
        } else {
            previousSelectedMarker = null;
        }
        view.removePolyLine();
        view.deselectMultipleMarker(markerDestination);
    }

    @Override
    public void processMarker(Marker clickedMarker) {

        if(clickedMarker.getTag() != null) {
            if (clickedMarker.getTag().equals("origin")) {
                view.showToast("My location");
                return;
            }
        }

        MarkerInfo markerInfo = (MarkerInfo) clickedMarker.getTag();

        DriveRequest request = new DriveRequest();

        String origin = null;
        String destination = null;

        LatLng start = null;
        LatLng end = null;

        if (previousSelectedMarker != null) {
            if (clickedMarker.equals(previousSelectedMarker)) {
                routeOrder.remove(clickedMarker);

                markerInfo.setSelected(false);

                if(markerInfo.isBusiness()){
                    markerInfo.setIconType("business");
                }else{
                    markerInfo.setIconType("private");
                }

                int routeSize = routeOrder.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = routeOrder.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }

                view.removePolyLine();
                view.changeMarkerIcon(clickedMarker);
                view.deselectMarker();

            } else {
                if (markerInfo.isSelected()) {
                    view.showSnackBar(routeOrder.indexOf(clickedMarker));
                }else{
                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    start = previousSelectedMarker.getPosition();
                    end = clickedMarker.getPosition();

                    markerInfo.setSelected(true);
                    markerInfo.setIconType("selected");

                    routeOrder.add(clickedMarker);
                    previousSelectedMarker = clickedMarker;
                    view.changeMarkerIcon(clickedMarker);
                }
            }
        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            start = new LatLng(52.008234,4.312999);
            end = clickedMarker.getPosition();

            markerInfo.setSelected(true);
            markerInfo.setIconType("selected");

            routeOrder.add(clickedMarker);
            previousSelectedMarker = clickedMarker;
            view.changeMarkerIcon(clickedMarker);
        }

        if(origin != null && destination != null) {
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getPolylineToMarker(start, end);
            view.getDriveInformation(request);
        }
    }
}