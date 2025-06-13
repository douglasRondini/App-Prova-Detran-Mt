package com.example.provamendes.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.provamendes.data.model.Aluno

class SharedViewModel : ViewModel() {
    private val _itemList = MutableLiveData<List<Aluno>>(emptyList())
    val itemList: LiveData<List<Aluno>> get() = _itemList

    fun addItem(aluno: Aluno) {
        val listaAtualizada = _itemList.value.orEmpty().toMutableList()
        listaAtualizada.add(aluno)
        _itemList.value = listaAtualizada
    }
    fun updateList(listaAtualizada: List<Aluno>) {
        _itemList.value = listaAtualizada
    }
}