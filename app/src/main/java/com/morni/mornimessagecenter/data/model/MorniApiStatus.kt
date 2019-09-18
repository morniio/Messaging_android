package com.morni.mornimessagecenter.data.model

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
enum class MorniApiStatus {
    LOADING,
    INITIAL_LOADING,
    SUCCESS,
    INITIAL_SUCCESS,
    EMPTY_DATA,
    NO_INTERNET,
    INITIAL_NO_INTERNET,
    INITIAL_ERROR,
    ERROR,
    UN_AUTHORIZED
}