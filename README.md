# CATSIGAR
Proyecto que combina realidad aumentada con información catastral


Para desplegar el proyecto en Android Studio
## Clonar el repositorio

```bash
git clone https://github.com/seralra96/CATSIGAR.git
```

## Debes Solicitar una api key a Wikitude

Para solicitar la llave debes crear una cuenta en la página de [Wikitude](https://www.wikitude.com/)

El valor de la api key debe consignarse en el archivo res/values/wikitude_api_key.xml de la siguiente forma: 

```html

<?xml version="1.0" encoding="utf-8"?>
<resources>
<string name="wikitude_license_key">Ingresa la api key aquí</string>
</resources>

```

## Debes Solicitar una api key de Google Maps

Este valor debe ingresarse en el Android_Manifest, para conseguirla haz el siguiente [Tutorial](https://developers.google.com/maps/documentation/android-sdk/signup)

```html
        <meta-data
            android:name="com.google.android.maps.v2.API_KEY"
            android:value="Tu llave aquí" />

```

## Autor

* **Sergio Ramírez** - *Proyecto de investigación* - [seralra96](https://github.com/seralra96)
