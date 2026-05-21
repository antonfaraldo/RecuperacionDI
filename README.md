# Sistema de Gestión de Vehículos y Usuarios - JavaFX & MySQL

## 📝 Descripción del Proyecto
Este proyecto es una aplicación de escritorio interactiva desarrollada en **JavaFX** y persistida en una base de datos relacional **MySQL**, diseñada bajo una arquitectura modular robusta para la evaluación final del módulo de **Desarrollo de Interfaces** (2º de DAM).

La aplicación permite la administración completa de un catálogo de vehículos y un panel de gestión de usuarios, implementando políticas estrictas de seguridad criptográfica, control de accesos basado en roles.

---

## 🚀 Características Principales

### 1. Seguridad Criptográfica y Validación de Directivas
* **Encriptación con BCrypt:** Las contraseñas de los usuarios no se guardan en texto plano en la base de datos; se procesan mediante un algoritmo de hashing seguro utilizando la librería `jbcrypt`.
* **Sincronización de Políticas de Contraseñas:** Se obliga a cumplir una directiva de claves fuertes (mínimo 6 caracteres, inclusión obligatoria de letras y números) sincronizada tanto en el formulario de registro público de usuarios como en el flujo CRUD interno de creación de cuentas por parte del Administrador.

### 2. Control de Accesos Basado en Roles (RBAC)
* **Perfil Administrador (`admin`):** Acceso total al Panel de Usuarios (Operaciones CRUD completas de creación, modificación de datos, cambios de rol y eliminación) y permisos exclusivos para dar de alta nuevos vehículos (`Create Car`), editar parámetros y eliminar registros del catálogo.
* **Perfil Estándar (`user`):** Interfaz restringida de forma física y lógica. Se ocultan y deshabilitan dinámicamente los botones de acceso administrativo en el menú de navegación (`Navbar`) y en las tarjetas del catálogo para evitar accesos no autorizados.

### 3. Catálogo Reactivo y Tarjetas Personalizadas (Custom Cards)
* **Filtrado Predictivo en Tiempo Real:** Implementación de un cuadro de búsqueda predictivo vinculado a una lista filtrada (`FilteredList`) que evalúa de manera reactiva y en caliente el segmento o tipo de vehículo introducido por el usuario sin recargar la escena.
* **Carga Omnicanal de Imágenes:** El visor de imágenes nativo (`ImageView`) está preparado de forma síncrona para interpretar y renderizar fotografías desde tres orígenes distintos:
    1.  *URLs web remotas (Enlaces directos de internet).*
    2.  *Recursos internos empaquetados en el Classpath de la aplicación (`/images/`).*
    3.  *Rutas absolutas del disco duro local del equipo (`file:/`), convirtiendo dinámicamente las entradas locales mediante `java.io.File`.*

### 4. Sistema Relacional de Favoritos Exclusivo
* **Restricción Única:** Mediante restricciones de clave primaria en la tabla intermedia `user_favorites`, se asegura que cada usuario de la aplicación pueda tener únicamente **un solo coche seleccionado como favorito a la vez**.
* **Persistencia Atómica (`ON DUPLICATE KEY UPDATE`):** Al cambiar de favorito, el sistema actualiza de manera atómica el registro en el servidor sin necesidad de borrar el anterior ni lanzar excepciones SQL.

### 5. Interfaz de Usuario Avanzada (CSS Externo)
* Toda la lógica de presentación visual se encuentra desacoplada del código Java y delegada en el fichero **`estilos.css`**:
    * *Navbar Interactiva:* Botones de navegación con efectos de transición cromática en eventos *Hover*.
    * *Favorito del Usuario:* Bordes en verde corporativo y fondos suaves para identificar al instante el coche elegido por la sesión actual.
    * *Coche Líder Global:* Consulta agregada SQL en segundo plano para calcular cuál es el coche más votado por todos los usuarios del sistema, mutando su tarjeta a color oro e inyectando dinámicamente una corona decorativa `👑` de liderazgo.

---

## 🛠️ Tecnologías y Librerías Utilizadas
* **Lenguaje:** Java 21 / 25
* **Framework Gráfico:** JavaFX 21 (Componentes FXML)
* **Base de Datos:** MySQL 8.0 (Conector `mysql-connector-j`)
* **Gestor de Dependencias:** Maven
* **Seguridad:** JBCrypt 0.4 (org.mindrot)

---

## 🏗️ Patrones de Diseño Aplicados
* **MVC (Modelo-Vista-Controlador):** Separación estricta entre las entidades de datos (`model`), las vistas de maquetación visual (`FXML`) y la lógica de control del negocio (`controller`).
* **DAO (Data Access Object):** Encapsulación completa de las sentencias SQL y accesos a bases de datos a través de interfaces (`CarDAO`, `UserDAO`) e implementaciones aisladas.
* **Singleton (AppShell):** Coordinador de navegación centralizado encargado de administrar el enrutamiento de vistas, controlar el estado de la sesión activa del usuario y reconfigurar la escena de la ventana principal de manera limpia al iniciar o cerrar sesión (`Logout`).

---

## ⚙️ Instrucciones de Despliegue e Instalación

### 1. Preparación de la Base de Datos
Abre tu gestor de base de datos (ej. MySQL Workbench) y ejecuta íntegramente el archivo estructurado situado en la carpeta del proyecto:
```bash
# Ubicación del archivo de inicialización
/RecuperacionFinal/sql/script.sql
### 2. Luego el codigo se runnea en la clase App.java con esta ruta: 
src/main/java/com/dam/DI/RecuperacionFinal/App.java
