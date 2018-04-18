package com.example.avjindersinghsekhon.minimaltodo.Receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.Network.NetworkManager;
import com.example.avjindersinghsekhon.minimaltodo.R;

import static com.example.avjindersinghsekhon.minimaltodo.Receipt.ReceiptConfActivity.MERCHANT_CODE;
import static com.example.avjindersinghsekhon.minimaltodo.Receipt.ReceiptConfActivity.TRANSACTION_CODE;

public class ReceiptConfFragment extends AppDefaultFragment {


    private TextView mVersionTextView;
    private String appVersion = "0.1";
    private Toolbar toolbar;
    private TextView contactMe;
    private AnalyticsApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        app = (AnalyticsApplication) getActivity().getApplication();
        app.send(this);
        Intent intent = getActivity().getIntent();
        String transCode = intent.getStringExtra(TRANSACTION_CODE);
        String merchCode = intent.getStringExtra(MERCHANT_CODE);

        TextView receipt=view.findViewById(R.id.receipt);
        receipt.setText(MERCHANT_CODE);

        NetworkManager.getInstance().getData(transCode,merchCode);
    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_receipt_conf;
    }

    public static ReceiptConfFragment newInstance() {
        return new ReceiptConfFragment();
    }
}
