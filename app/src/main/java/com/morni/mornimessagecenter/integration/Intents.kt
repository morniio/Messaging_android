package com.morni.mornimessagecenter.integration

/**
 * Created by Rami El-bouhi on 17,September,2019
 */

class Intents {

    companion object {
        /**
         * Send this intent to open the Barcodes app in scanning mode, find a barcode, and return
         * the results.
         */
        const val START_ACTION = "com.morni.mornimessagecenter.START"
        const val STATUS: String = "status"
        const val MESSAGE: String = "message"
        const val BASE_URL: String = "base_url"
        const val ACCESS_TOKEN: String = "access_token"
        const val LANGUAGE: String = "language"
        const val APP_VERSION: String = "app_version"
        const val PAGE_SIZE: String = "page_size"

        // default page size
        const val DEFAULT_PAGE_SIZE = 10
    }
}