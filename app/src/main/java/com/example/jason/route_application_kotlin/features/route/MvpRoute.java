package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.TravelInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import java.util.ArrayList;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setupFragments(ArrayList<FormattedAddress> validAddresses);

        void passSingleDrive(SingleDrive singleDrive);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void getRouteFromApi();

        void getTravelInformation(TravelInformationRequest travelInformationRequest);

        void onListItemClick(String address);

        void onGoButtonClick(String address);

    }

    interface Interactor{
        void getTravelInformation(ApiPresenterCallBack apiPresenterCallBack, TravelInformationRequest request);
        void getOrganizedRouteFromApi(ApiPresenterCallBack apiPresenterCallBack, String routeCode);
    }

}
