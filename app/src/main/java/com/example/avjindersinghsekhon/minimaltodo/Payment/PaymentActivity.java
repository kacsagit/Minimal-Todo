package com.example.avjindersinghsekhon.minimaltodo.Payment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Receipt.ReceiptConfActivity;
import com.sumup.merchant.Models.TransactionInfo;
import com.sumup.merchant.api.SumUpAPI;
import com.sumup.merchant.api.SumUpLogin;

public class PaymentActivity extends AppDefaultActivity {

    private TextView mVersionTextView;
    private String appVersion = "0.1";
    private Toolbar toolbar;
    private TextView contactMe;
    String theme;
    //    private UUID mId;
    private AnalyticsApplication app;
    static final int REQUEST_CODE_LOGIN = 1;
    static final int REQUEST_CODE_PAYMENT = 2;
    private static final int REQUEST_CODE_PAYMENT_SETTINGS = 3;
    static final String AFFILITE_KEY = "4069e3f8-3851-4ace-97ad-da7e98aac8e6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        theme = getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
        if (theme.equals(MainFragment.DARKTHEME)) {
            Log.d("OskarSchindler", "One");
            setTheme(R.style.CustomStyle_DarkTheme);
        } else {
            Log.d("OskarSchindler", "One");
            setTheme(R.style.CustomStyle_LightTheme);
        }

        super.onCreate(savedInstanceState);
//        mId = (UUID)i.getSerializableExtra(TodoNotificationService.TODOUUID);

        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        if (backArrow != null) {
            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }
        SumUpLogin sumupLogin = SumUpLogin.builder(AFFILITE_KEY).build();
        SumUpAPI.openLoginActivity(this, sumupLogin, REQUEST_CODE_LOGIN);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_payment;
    }

    @NonNull
    protected Fragment createInitialFragment() {
        return PaymentFragment.newInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.payment:
                SumUpAPI.openPaymentSettingsActivity(this, REQUEST_CODE_PAYMENT_SETTINGS);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                if (data != null) {
                    Bundle extra = data.getExtras();
                    Log.d("Result code: ", String.valueOf(extra.getInt(SumUpAPI.Response.RESULT_CODE)));
                    Log.d("Message: ", extra.getString(SumUpAPI.Response.MESSAGE));

                }
                break;
            case REQUEST_CODE_PAYMENT:
                if (data != null) {
                    Bundle extra = data.getExtras();
                    int result = extra.getInt(SumUpAPI.Response.RESULT_CODE);
                    Log.d("Result code: ", String.valueOf(result));
                    Log.d("Message: ", extra.getString(SumUpAPI.Response.MESSAGE));


                    String txCode = extra.getString(SumUpAPI.Response.TX_CODE);
                    Log.d("Transaction Code: ", txCode == null ? "" : "Transaction Code: " + txCode);

                    boolean receiptSent = extra.getBoolean(SumUpAPI.Response.RECEIPT_SENT);
                    Log.d("Receipt sent: ", String.valueOf(receiptSent));

                    TransactionInfo transactionInfo = extra.getParcelable(SumUpAPI.Response.TX_INFO);
                    Log.d("Transaction Info: ", String.valueOf(transactionInfo));

                    if (result == SumUpAPI.Response.ResultCode.SUCCESSFUL) {
                        Intent intent = new Intent(this, ReceiptConfActivity.class);
                        intent.putExtra(ReceiptConfActivity.TRANSACTION_CODE, txCode);
                        intent.putExtra(ReceiptConfActivity.MERCHANT_CODE, transactionInfo.getMerchantCode());
                        startActivity(intent);
                    }
                }
                break;

            case REQUEST_CODE_PAYMENT_SETTINGS:
                if (data != null) {
                    Bundle extra = data.getExtras();
                    int result = extra.getInt(SumUpAPI.Response.RESULT_CODE);
                    Log.d("Result code: ", String.valueOf(result));
                    Log.d("Message: ", extra.getString(SumUpAPI.Response.MESSAGE));
                }
                break;

            default:
                break;
        }
    }

}
