package com.example.e_ocorrencias.utils// StateExtensions.kt

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import kotlin.reflect.KProperty

operator fun <T> State<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

operator fun <T> MutableState<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
}