package com.e.occano.di

import com.e.occano.di.auth.AuthComponent
import com.e.occano.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        AuthComponent::class,
        MainComponent::class
    ]
)
class SubComponentsModule