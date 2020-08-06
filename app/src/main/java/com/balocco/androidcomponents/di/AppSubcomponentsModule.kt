package com.balocco.androidcomponents.di

import com.balocco.androidcomponents.feature.toprated.di.TopRatedComponent
import dagger.Module

@Module(
    subcomponents = [
        TopRatedComponent::class]
)
class AppSubcomponentsModule