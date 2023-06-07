package com.example.test.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.model.PayloadProduct
import com.example.test.repositories.MainRepository
import com.example.test.utils.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("mainRepository") private val repository: MainRepository
) : ViewModel() {

    private val _status: MutableLiveData<ApiStatus> = MutableLiveData()
    val status: LiveData<ApiStatus> get() = _status
    private val _productData: MutableLiveData<PayloadProduct> = MutableLiveData()
    val productData: LiveData<PayloadProduct> get() = _productData

    fun getProducts(){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            repository.getProducts().collect{
                try{
                    _productData.value = it
                    _status.value = ApiStatus.COMPLETE
                }
                catch (_ : HttpException){
                    _status.value = ApiStatus.FAILED
                }
            }
        }
    }
}