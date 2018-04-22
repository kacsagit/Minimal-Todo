package com.example.avjindersinghsekhon.minimaltodo.Network;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.sumup.merchant.Models.TransactionInfo;

public class Receipt {

    @SerializedName("transaction_data")
    TransactionData transactionData;
    @SerializedName("merchant_data")
    JsonObject merchantData;
    @SerializedName("acquirer_data")
    JsonObject acquirerData;

    public TransactionData getTransactionData() {
        return transactionData;
    }

}
