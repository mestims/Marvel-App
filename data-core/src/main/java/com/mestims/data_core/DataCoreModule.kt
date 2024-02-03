package com.mestims.data_core

import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Calendar
import java.util.TimeZone

val module = module {

    factory {
        AuthorizationInterceptor(
            get(named("")),
            get(),
            Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        )
    }

}