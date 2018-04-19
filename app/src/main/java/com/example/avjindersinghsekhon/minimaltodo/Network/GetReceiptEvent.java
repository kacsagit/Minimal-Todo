package com.example.avjindersinghsekhon.minimaltodo.Network;

public class GetReceiptEvent<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}