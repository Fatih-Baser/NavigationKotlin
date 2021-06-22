package com.fatihbaser.footballapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihbaser.footballapp.model.leaguetable.LeagueTableResponse
import com.fatihbaser.footballapp.model.leaguetable.Standing
import com.fatihbaser.footballapp.remotedata.ApiClient

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LeagueTableViewModel: ViewModel() {

    private val apiClient =  ApiClient()
    private val disposable = CompositeDisposable()

    val leagueTable = MutableLiveData<List<List<Standing>>>()
    val loadingLeagueTable = MutableLiveData<Boolean>()

    fun getLeagueTable(leagueId: Int){
        loadingLeagueTable.value = true
        disposable.add(
            apiClient.getLeagueTable(leagueId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LeagueTableResponse>(){
                    override fun onSuccess(t: LeagueTableResponse) {
                        leagueTable.value = t.api.standings
                        loadingLeagueTable.value = false
                        Log.i("calıstı", " calıstı")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("hata", "  "+ e.printStackTrace() + " "+ e.message)

                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}