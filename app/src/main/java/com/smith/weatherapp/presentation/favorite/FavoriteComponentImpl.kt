package com.smith.weatherapp.presentation.favorite

import com.arkivanov.decompose.ComponentContext

class FavoriteComponentImpl(
    componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext