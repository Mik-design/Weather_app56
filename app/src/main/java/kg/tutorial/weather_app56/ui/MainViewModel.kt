package kg.tutorial.weather_app56.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kg.tutorial.weather_app56.repo.WeatherRepo


class MainViewModel(private val repo: WeatherRepo) : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    private val isLoading = MutableLiveData<Boolean>()
    val _isLoading: LiveData<Boolean>
        get() = isLoading

    init {
        getWeatherFromApi()
    }

    fun getWeatherFromApi() {
        compositeDisposable.add(
            repo.getWeatherFromApi()
                .doOnTerminate {hideLoading()}
                .subscribe({}, {})
        )
    }

    fun showLoading() {
        isLoading.value = true
    }

    private fun hideLoading() {
        isLoading.value = false

    }


    fun getForeCastAsLive() = repo.getForeCastFromDbAsLive()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}