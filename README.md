# 💪 VibraFit - App Android

<img width="363" height="788" alt="image" src="https://github.com/user-attachments/assets/929e2af6-5289-4892-a518-286e48bd30ff" />

<img width="372" height="773" alt="image" src="https://github.com/user-attachments/assets/71421890-f6f3-4477-bf8e-d5db7a6b8773" />



Aplicación móvil Android para la gestión de un gimnasio. Permite a clientes y administradores acceder a su información personalizada, rutinas de entrenamiento y perfil de usuario.

---

## 📱 Pantallas

| Splash | Login | Home (Cliente) | Admin |
|--------|-------|----------------|-------|
| Bienvenida con logo | Autenticación por rol | Rutinas, progreso y datos físicos | Panel de administración |

---

## 🏗️ Arquitectura

```
VibraFitAPK/
├── activities/
│   ├── SplashActivity.kt         # Pantalla de bienvenida
│   ├── LoginActivity.kt          # Autenticación
│   ├── MainActivity.kt           # Contenedor CLIENTE (BottomNav)
│   └── AdminMainActivity.kt      # Panel ADMINISTRADOR
├── fragments/
│   ├── HomeFragment.kt           # Dashboard cliente
│   ├── ProfileFragment.kt        # Perfil del usuario
│   └── SettingsFragment.kt       # Configuración y logout
├── models/
│   ├── LoginRequest.kt
│   ├── LoginResponse.kt
│   └── Usuario.kt
├── network/
│   ├── ApiService.kt             # Endpoints REST
│   └── RetrofitClient.kt         # Configuración Retrofit
├── utils/
│   └── SessionManager.kt         # Sesión cifrada (EncryptedSharedPreferences)
└── viewmodels/
    ├── LoginViewModel.kt
    └── LoginViewModelFactory.kt
```

---

## 🔐 Autenticación y Roles

El sistema de login redirige automáticamente según el rol del usuario:

```
Login exitoso
├── ROLE_CLIENTE       → MainActivity (HomeFragment)
├── ROLE_ADMINISTRADOR → AdminMainActivity
└── ROLE_ENTRENADOR    → EntrenadorMainActivity (próximamente)
```

La sesión se almacena de forma segura usando **EncryptedSharedPreferences (AES-256)**.

---

## 🌐 Conexión con el Backend

La app se conecta a una API REST desarrollada en **Spring Boot** con base de datos **MySQL**.

### Endpoints usados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/auth/login` | Autenticación de usuario |
| `GET` | `/api/usuarios/{id}` | Obtener datos del usuario |
| `GET` | `/api/usuarios/perfil` | Perfil del usuario autenticado |

### Configuración de URL

```kotlin
// RetrofitClient.kt
// Emulador Android Studio
private const val BASE_URL = "http://10.0.2.2:8080/"

// Dispositivo físico (reemplazar con IP local)
// private const val BASE_URL = "http://192.168.1.XXX:8080/"
```

---

## 🛠️ Tecnologías

### Android
- **Lenguaje:** Kotlin
- **Arquitectura:** MVVM (ViewModel + LiveData)
- **Navegación:** Fragment + BottomNavigationView
- **Red:** Retrofit 2 + OkHttp + Gson
- **Seguridad:** EncryptedSharedPreferences (AES-256)
- **UI:** Material Design 3 + ViewBinding
- **Async:** Coroutines + ViewModelScope

### Backend (Spring Boot)
- **Framework:** Spring Boot 3
- **Seguridad:** Spring Security + BCrypt
- **Base de datos:** MySQL
- **ORM:** JPA / Hibernate

---

## ⚙️ Configuración del proyecto

### Requisitos previos
- Android Studio Hedgehog o superior
- JDK 17+
- Emulador Android API 24+ o dispositivo físico
- Backend Spring Boot corriendo en `localhost:8080`



### Dependencias principales (`build.gradle`)

```gradle
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// ViewModel + LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Security
implementation("androidx.security:security-crypto:1.1.0-alpha06")

// Fragment
implementation("androidx.fragment:fragment-ktx:1.6.2")
```


## 🚧 Próximamente

- [ ] Pantalla de entrenador
- [ ] Gestión de rutinas
- [ ] Notificaciones push
- [ ] Historial de entrenamientos
- [ ] Gestión de membresías desde la app

---

## 👥 Roles del sistema

| Rol | Acceso |
|-----|--------|
| `ROLE_CLIENTE` | App Android - Dashboard personal |
| `ROLE_ADMINISTRADOR` | App Android - Panel admin + Web |
| `ROLE_ENTRENADOR` | Web (app móvil próximamente) |

---



> Desarrollado con ❤️ usando Kotlin + Spring Boot
