# Config Lab
Proyecto en donde se creara multiples ramas para distintas configuracione.

## develop

La Rama develop tendrá la primera configuración.

## Configuración
local.properties -> android.defaults.buildfeatures.buildconfig=true

buildFeatures{
     viewBinding = true -> activar binding
     buildConfig = true -> activar buildConfig
}

Agregar DaggerHilt

## gradle nivel del proyecto
id("com.google.dagger.hilt.android") version "2.51.1" apply false 

## gradle nivel de app

plugins {
id("kotlin-kapt")
id("com.google.dagger.hilt.android")
}

android {
...
}

dependencies {
implementation("com.google.dagger:hilt-android:2.51.1")
kapt("com.google.dagger:hilt-android-compiler:2.51.1")
}

// Allow references to generated code
kapt {
correctErrorTypes = true
}

