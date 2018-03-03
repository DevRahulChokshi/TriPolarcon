package com.example.ebc003.tripolarcon.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestEditTrading extends AppCompatActivity {

    @BindView(R.id.btnEdit) Button mBtnEdit;

    @BindView(R.id.txtTradingServiceDateData) EditText mTradingServiceDate;
    @BindView (R.id.txtOrderDescriptionData) EditText mOrderDescription;
    @BindView (R.id.txtProductAreaData) EditText mArea;
    @BindView (R.id.txtProductUnitData) EditText mUnit;
    @BindView (R.id.txtServiceSourceTypeData) EditText mSourceType;
    @BindView (R.id.txtServiceRequirementRemarkData) EditText mRequirementRemark;
    @BindView (R.id.txtServiceAssignToData) EditText mAssignTo;
    @BindView (R.id.txtServiceGeneratedToData) EditText mGeneratedTo;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_test_edit_trading);

        ButterKnife.bind (this);

        mTradingServiceDate.setEnabled(false);
        mTradingServiceDate.setInputType(InputType.TYPE_NULL);
        mTradingServiceDate.setFocusable(false);

        mBtnEdit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                mTradingServiceDate.setVisibility (View.VISIBLE);
            }
        });
    }
}
