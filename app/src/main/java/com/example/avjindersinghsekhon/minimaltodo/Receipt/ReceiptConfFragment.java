package com.example.avjindersinghsekhon.minimaltodo.Receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.Network.ErrorEvent;
import com.example.avjindersinghsekhon.minimaltodo.Network.GetReceiptEvent;
import com.example.avjindersinghsekhon.minimaltodo.Network.NetworkManager;
import com.example.avjindersinghsekhon.minimaltodo.Network.NoInternetEvent;
import com.example.avjindersinghsekhon.minimaltodo.Network.Receipt;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.sumup.merchant.Models.TransactionInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.avjindersinghsekhon.minimaltodo.Receipt.ReceiptConfActivity.MERCHANT_CODE;
import static com.example.avjindersinghsekhon.minimaltodo.Receipt.ReceiptConfActivity.TRANSACTION_CODE;

public class ReceiptConfFragment extends AppDefaultFragment {

    private AnalyticsApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetReceipt(GetReceiptEvent<Receipt> event) {
        TransactionInfo transaction = event.getData().getTransactionData();
        amount.setText(String.valueOf(transaction.getAmount()));
        currency.setText(transaction.getCurrency());
        status.setText(transaction.getStatus());
        card.setText(transaction.getCard().getType());
        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetError(ErrorEvent<String> event) {
        String error = event.getData();
        Snackbar.make(linearLayout, error, Snackbar.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoInternet(NoInternetEvent event) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(linearLayout, R.string.no_internt, Snackbar.LENGTH_LONG).show();
    }


    TextView amount;
    TextView currency;
    TextView status;
    TextView card;
    ProgressBar progressBar;
    LinearLayout linearLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        app = (AnalyticsApplication) getActivity().getApplication();
        app.send(this);
        Intent intent = getActivity().getIntent();
        String transCode = intent.getStringExtra(TRANSACTION_CODE);
        String merchCode = intent.getStringExtra(MERCHANT_CODE);

        progressBar = view.findViewById(R.id.progressBar);
        amount = view.findViewById(R.id.amount);
        linearLayout = view.findViewById(R.id.linearLayout);
        currency = view.findViewById(R.id.currency);
        status = view.findViewById(R.id.status);
        card = view.findViewById(R.id.card);


        NetworkManager.getInstance().getReceipt(getContext(), transCode, merchCode);
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
