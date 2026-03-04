## VibraFit APK 🏋️‍♂️📱
<img width="303" height="650" alt="image" src="https://github.com/user-attachments/assets/0d817b8b-8d5d-40a1-9412-cd19b478a327" />
<img width="342" height="654" alt="image" src="https://github.com/user-attachments/assets/51e03f46-d961-40a5-b38e-71d23bb0bf87" />



# 🏋️‍♂️ VibraFit APK - Gestión de Entrenamiento

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

**VibraFit** es una aplicación móvil nativa diseñada para optimizar la gestión de usuarios y rutinas de ejercicio. Conecta una interfaz de usuario fluida en Android con un robusto backend en **Spring Boot**, permitiendo un control total administrativo y una experiencia personalizada para el deportista.

---

## 📱 Vista Previa de la Arquitectura


## ✨ Características Principales

### 🔐 Seguridad y Sesión
* **Encrypted Storage**: Implementación de `EncryptedSharedPreferences` para el manejo de tokens JWT y datos de perfil.
* **Session Manager**: Control persistente del estado de login y recuperación de datos de usuario en tiempo real.

### 🛠️ Panel Administrativo (CRUD)
* **Visualización Dinámica**: Listado de usuarios mediante `RecyclerView` con soporte para diferentes roles.
* **Gestión de Usuarios**: Funcionalidades completas para Crear, Leer, Editar y Eliminar (CRUD) directamente desde la APK.
* **Interfaz Adaptativa**: El menú lateral y las opciones cambian automáticamente si el sistema detecta el rol `ROLE_ADMINISTRADOR`.

### 🌐 Comunicación de Red
* **Retrofit 2**: Cliente HTTP optimizado con interceptores de logueo para depuración.
* **Corrutinas**: Manejo asíncrono de peticiones para mantener la fluidez de la interfaz (UI Thread libre).

---

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
| :--- | :--- |
| **Lenguaje** | Kotlin 1.9+ |
| **Arquitectura** | MVVM (Model-View-ViewModel) |
| **Diseño** | ViewBinding & Material Design 3 |
| **Red** | Retrofit 2.9.0 / Gson |
| **Backend** | Spring Boot / Java / JPA |

---

## 📂 Estructura del Proyecto

```text
com.octopus.vibrafit
├── activities     # LoginActivity, MainActivity, Splash
├── adapters       # UsuarioAdapter (Manejo de listas)
├── fragments      # Home, Profile, AdminDashboard, Settings
├── models         # LoginRequest, LoginResponse, Usuario
├── network        # ApiService, RetrofitClient
├── utils          # SessionManager, Constants
└── viewmodels     # Lógica de negocio (LoginViewModel, UsuarioViewModel)
activities: Pantallas principales como LoginActivity y MainActivity.

fragments: Módulos de la UI (Home, Profile, AdminDashboard, etc.).

models: Clases de datos para peticiones y respuestas de la API.

network: Configuración de RetrofitClient e interfaz ApiService.

utils: Utilidades de sistema como SessionManager.

viewmodels: Lógica de negocio y manejo de estado de la UI.
