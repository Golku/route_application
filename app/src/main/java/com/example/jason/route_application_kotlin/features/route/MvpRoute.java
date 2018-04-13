package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setupFragments(RouteInfoHolder routeInfoHolder);

        void updateRouteEndTime(String endTime);

        void delegateRouteChange(RouteListFragmentDelegation delegation);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void initializeRoute(Route route);

        void getDriveInformation(SingleDriveRequest request);

        void markerDeselected(String destination);

        void multipleMarkersDeselected(String destination);

        void onListItemClick(String address);

        void onGoButtonClick(String address);
    }

    interface Interactor{

        void getDriveInformation(ApiCallback.SingleDriveResponseCallback callback, SingleDriveRequest request);

    }

}
