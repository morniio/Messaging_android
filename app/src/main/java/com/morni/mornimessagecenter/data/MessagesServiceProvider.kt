package com.morni.mornimessagecenter.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Abdulmajeed Alyafey on 2019-12-29.
 *
 * This class acts as utility for providing services to caller with no UI.
 * Services may be remote or local services.
 */
object MessagesServiceProvider {

    fun getTotalUnreadMessages(
        repository: Repository,
        func: (Int?) -> Unit
    ) {
        val disposable = CompositeDisposable()
        disposable.add(repository.apiService.getTotalUnreadMessages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    func(response.data?.totalUnread)
                    disposable.dispose()
                },
                {
                    func(null)
                    disposable.dispose()
                }
            )
        )
    }
}