package com.example.jason.route_application.features.commentInput;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.pojos.database.CommentInputResponse;
import com.example.jason.route_application.data.pojos.Address;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

/**
 * Created by Jason on 19-Feb-18.
 */

public class CommentInputPresenter implements MvpCommentInput.Presenter, DatabaseCallback.CommentInputCallBack {

    private final MvpCommentInput.View view;
    private MvpCommentInput.Interactor interactor;

    private Address address;
    private String employeeId;
    private String date;

    @Inject
    public CommentInputPresenter(MvpCommentInput.View view, MvpCommentInput.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setUpInfo(String employeeId, Address address){
        this.employeeId = employeeId;
        this.address = address;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.date = sdf.format(System.currentTimeMillis());
        view.updateTextViews(employeeId, date);
    }

    @Override
    public void onAddCommentBtnClick(String comment) {
        view.onStartNetworkOperation();
        interactor.addCommentToAddress(this, address, employeeId, comment, date);
    }

    @Override
    public void onCommentInputResponse(CommentInputResponse response) {
        view.onFinishNetworkOperation();

        if(response.isSuccess()){
            view.showToast("Comment was added");
            view.closeActivity();
        }else{
            view.showToast("Fail to add list_item_comment");
        }
    }

    @Override
    public void onCommentInputResponseFailure() {
        view.showToast("Unable to connect to the database");
        view.closeActivity();
    }
}