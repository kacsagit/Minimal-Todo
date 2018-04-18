package com.example.avjindersinghsekhon.minimaltodo.Payment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.sumup.merchant.api.SumUpAPI;
import com.sumup.merchant.api.SumUpPayment;

import java.math.BigDecimal;

public class PaymentFragment extends AppDefaultFragment {


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

        //TODO check if bigger than 1
        final EditText amount=view.findViewById(R.id.amount_et);
        Button submit=view.findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SumUpPayment payment = SumUpPayment.builder()
                        // mandatory parameters
                        .total(new BigDecimal(amount.getText().toString())) // minimum 1.00
                        .currency(SumUpPayment.Currency.EUR)
                        // optional: add details
                        .title("Payment")
                        .receiptEmail("katajona@gmail.com")
                        .receiptSMS("+36203639701")
                        .build();

                SumUpAPI.checkout(getActivity(), payment, PaymentActivity.REQUEST_CODE_PAYMENT);
            }
        });



    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_payment_conf;
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }
}
