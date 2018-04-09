package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class RouteMapPresenter implements MvpRouteMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpRouteMap.View view;

    private List<Marker> routeOrder;

    private Marker previousSelectedMarker;

    RouteMapPresenter(MvpRouteMap.View view) {
        this.view = view;
        this.routeOrder = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMarkers(List<FormattedAddress> addressList) {
        view.addMarkersToMap(addressList);
    }

    @Override
    public void processMarker(Marker clickedMarker) {
        if(clickedMarker.getTag() != null) {
            if (clickedMarker.getTag().equals("origin")) {
                view.showToast("origin");
                return;
            }
        }

        SingleDriveRequest request = new SingleDriveRequest();
        String origin = null;
        String destination = null;

        LatLng start = null;
        LatLng end = null;

        if (previousSelectedMarker != null) {
            if (clickedMarker.equals(previousSelectedMarker)) {
                clickedMarker.setTag(true);
                routeOrder.remove(clickedMarker);
                clickedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_private_address2));

                int routeSize = routeOrder.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = routeOrder.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }

                view.removePolyLine();
                view.removeAddress(clickedMarker.getTitle());

            } else {
                boolean newMarker = (boolean) clickedMarker.getTag();

                if (newMarker) {
                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    start = previousSelectedMarker.getPosition();
                    end = clickedMarker.getPosition();
                    clickedMarker.setTag(false);
                    routeOrder.add(clickedMarker);
                    clickedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    previousSelectedMarker = clickedMarker;
                }else{
//                    ask the user if he wants to remove all after this one.
//                    view.showSnackBar(clickedMarker.getTitle());
                }
            }
        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            start = new LatLng(52.008234, 4.312999);
            end = clickedMarker.getPosition();
            clickedMarker.setTag(false);
            routeOrder.add(clickedMarker);
            clickedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            previousSelectedMarker = clickedMarker;
        }

        if(origin != null && destination != null) {
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getPolylineToMarker(start, end);
            view.getDriveInformation(request);
        }
    }
}
