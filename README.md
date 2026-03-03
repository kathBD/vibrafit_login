## VibraFit APK 🏋️‍♂️📱

- VibraFit es una solución integral para la gestión de entrenamientos y usuarios, compuesta por una aplicación móvil nativa en Android y un potente backend desarrollado en Spring Boot. Este repositorio contiene el código fuente de la aplicación móvil.

# 🚀 Características Principales
- Autenticación Segura: Inicio de sesión cifrado utilizando EncryptedSharedPreferences para proteger tokens y datos sensibles.

- Gestión de Roles: Interfaz adaptativa que cambia entre Panel de Administrador y Vista de Usuario según el perfil detectado.

- CRUD de Usuarios: Panel administrativo completo para visualizar, crear, editar y eliminar usuarios directamente desde la APK.

- Consumo de API REST: Integración fluida con servicios backend mediante Retrofit2 y Corrutinas de Kotlin.

- Interfaz Moderna: Uso de ViewBinding para una manipulación de vistas segura y eficiente.

🛠️ Stack Tecnológico
Lenguaje: Kotlin

Arquitectura: MVVM (Model-View-ViewModel)

Networking: Retrofit 2 & OkHttp

Inyección de Dependencias: Kotlin Coroutines

Seguridad: Android Jetpack Security (Crypto)

📦 Estructura del Proyecto
El proyecto sigue una organización coherente de paquetes bajo el namespace com.octopus.vibrafit:

activities: Pantallas principales como LoginActivity y MainActivity.

fragments: Módulos de la UI (Home, Profile, AdminDashboard, etc.).

models: Clases de datos para peticiones y respuestas de la API.

network: Configuración de RetrofitClient e interfaz ApiService.

utils: Utilidades de sistema como SessionManager.

viewmodels: Lógica de negocio y manejo de estado de la UI.
