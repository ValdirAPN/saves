package com.vpnt.saves.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val savesDispatcher: SavesDispatchers)

enum class SavesDispatchers {
    Default,
    IO,
}