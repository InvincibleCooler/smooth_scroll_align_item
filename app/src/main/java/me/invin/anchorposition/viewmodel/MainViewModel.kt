package me.invin.anchorposition.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.invin.anchorposition.adapter.MainAdapter


class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _dataList = MutableLiveData<ArrayList<MainWrappedData>>()
    val dataList: LiveData<ArrayList<MainWrappedData>> get() = _dataList

    fun requestData() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = ArrayList<MainWrappedData>()

            dataList.add(MainWrappedData(id = "JOB", viewType = MainAdapter.VIEW_TYPE_JOB))
            dataList.add(MainWrappedData(id = "ANIMAL", viewType = MainAdapter.VIEW_TYPE_ANIMAL))
            dataList.add(MainWrappedData(id = "IDOL", viewType = MainAdapter.VIEW_TYPE_IDOL))
            dataList.add(MainWrappedData(id = "COLOR", viewType = MainAdapter.VIEW_TYPE_COLOR))
            _dataList.postValue(dataList)
        }
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}

data class MainWrappedData(
    val id: String = "",
    val viewType: Int = View.NO_ID,
    val data: Any? = null
)

data class ColorData(val name: String = "", val code: String)