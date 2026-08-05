# ⚙️ Sistema de Encuestas de Satisfacción a Clientes (CXI) - Backend API

API RESTful desarrollada en Java y Spring Boot para la gestión de encuestas de satisfacción, cálculo de métricas NPS, sincronización de sucursales automotrices y procesamiento de datos masivos.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 17+)
* **Framework:** Spring Boot 3.x
  * **Spring Web:** Exposición de endpoints API REST.
  * **Spring Data JPA:** Mapeo objeto-relacional y persistencia.
  * **Spring Security:** Gestión de autenticación, roles y tokens de encuesta.
* **Base de Datos:** PostgreSQL / Oracle Database.
* **Gestor de Dependencias:** Maven.
* **Otras Herramientas:**
  * Apache POI / XLSX (para lectura y generación de reportes en Excel).
  * Lombok (para reducción de código boilerplate).

---

## 🚀 Arquitectura y Características

* 🔐 **Generación Segura de Encuestas:** Generación de tokens únicos e irremplazables para que los clientes respondan de forma individual y segura.
* 📊 **Procesamiento de Métricas y NPS Score:** Algoritmo dinámico que calcula promedios y clasifica promotores, pasivos y detractores en una escala de 1 a 5 estrellas.
* 🏢 **Multisucursal y Agencias Separadas:** Control independiente de datos y métricas para agencias como Subaru, Toyota Pachuca, Toyota Tulancingo, Carsline Pachuca, Carsline Querétaro, GWM, ComproCars, entre otras.
* 📁 **Carga Masiva de Archivos:** Procesamiento automático de datos en formato Excel para la generación de encuestas a gran escala.

---

## 🔧 Configuración del Proyecto

### 1. Prerrequisitos
* Java Development Kit (JDK 17 o superior).
* Maven 3.8+ instalado.
* Base de datos PostgreSQL o Oracle activa.

### 2. Configuración de Base de Datos
Asegúrate de configurar la conexión en el archivo `src/main/resources/application.properties` (o `application.yml`):

```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/db_encuestas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
