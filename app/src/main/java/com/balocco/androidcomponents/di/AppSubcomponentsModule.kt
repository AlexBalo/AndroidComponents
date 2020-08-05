package com.balocco.androidcomponents.di

import com.balocco.androidcomponents.feature.main.di.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class]
)
class AppSubcomponentsModule