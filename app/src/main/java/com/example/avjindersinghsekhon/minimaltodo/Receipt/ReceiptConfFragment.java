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
import com.example.avjindersinghsekhon.minimaltodo.Network.ErrorEvent;
import com.example.avjindersinghsekhon.minimaltodo.Network.GetReceiptEvent;
import com.example.avjindersinghsekhon.minimaltodo.Network.NetworkManager;
import com.example.avjindersinghsekhon.minimaltodo.Network.Receipt;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.sumup.merchant.Models.TransactionInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetReceipt(GetReceiptEvent<Receipt> event) {
        TransactionInfo transaction=event.getData().getTransactionData();
        receipt.setText(String.valueOf(transaction.getAmount()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetError(ErrorEvent<String> event) {
        String error=event.getData();
    }


    TextView receipt;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        app = (AnalyticsApplication) getActivity().getApplication();
        app.send(this);
        Intent intent = getActivity().getIntent();
        String transCode = intent.getStringExtra(TRANSACTION_CODE);
        String merchCode = intent.getStringExtra(MERCHANT_CODE);

        receipt = view.findViewById(R.id.receipt);

        NetworkManager.getInstance().getReceipt(transCode,merchCode);
    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_receipt_conf;
    }

    public static ReceiptConfFragment newInstance() {
        return new ReceiptConfFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
