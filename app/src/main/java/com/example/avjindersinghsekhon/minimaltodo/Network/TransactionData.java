package com.example.avjindersinghsekhon.minimaltodo.Network;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sumup.merchant.Models.TransactionInfo;

import java.util.List;

public class TransactionData {
    TransactionInfo.Card card;
    List<TransactionData.Product> products;
    @SerializedName("transaction_code")
    String transactionCode;
    Double amount;
    @SerializedName("tip_amount")
    Double tipAmount;
    @SerializedName("vat_amount")
    Double vatAmount;
    String currency;
    String status;
    @SerializedName("payment_type")
    String paymentType;
    @SerializedName("entry_mode")
    String entryMode;


    public TransactionInfo.Card getCard() {
        return this.card;
    }

    public String getTransactionCode() {
        return this.transactionCode;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Double getTipAmount() {
        return this.tipAmount;
    }

    public Double getVatAmount() {
        return this.vatAmount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public String getEntryMode() {
        return this.entryMode;
    }


    private static class Product{
        Double price;
        Integer quantity;
    }
}
