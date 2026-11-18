# Laboratorios de Desarrollo Web Avanzado (S10–S14)

Autor: **Nikolai Alexander Suarez Núñez** · Paquetes Java: `com.suarez`.

Proyecto progresivo con cinco carpetas de laboratorio y sus guías originales. Requisitos: JDK 17+ y Maven 3.9+.

## Módulos
- `semana10-empleados`: perfiles DEV/QA/TEST/PROD, variables de entorno, CRUD REST y `/configuracion`. Usa Spring Data JPA con H2 en archivo; `DB_URL`, `DB_USER` y `DB_PASSWORD` permiten configurar otra base compatible, como MySQL.
- `semana11-clinica`: APIs REST de pacientes, médicos y citas; su almacenamiento actual es en memoria y se reinicia al detener el servicio.
- `eureka-server`, `categoria-service`, `cliente-service`, `producto-service`, `pedido-service`: catálogo y pedidos con Eureka/OpenFeign.
- `gateway-service`: rutas Gateway para categoría, cliente, producto y pedido.
- `config-server`: por defecto obtiene configuración del repositorio GitHub de este proyecto. Para trabajar offline, iniciar con el perfil `native`, que lee `config-repo` local.

## Compilar
Desde esta carpeta: `mvn clean compile -DskipTests`.

## Ejecución sugerida
Inicia cada módulo en una terminal con `mvn -pl <modulo> spring-boot:run`. Orden: Config Server (8888), Eureka (8761), categoría (8082), cliente (8081), producto (8083), pedido (8084) y Gateway (8080). La clínica S11 usa el puerto 8091.

Ejemplo: `POST http://localhost:8081/api/clientes` con `{"nombre":"Nikolai","apellido":"Suarez","direccion":"Lima","nroDni":"12345678","estado":"A"}`. Después, consulta `GET http://localhost:8080/api/pedidos/{id}`. Semana 10: CRUD en `/empleados` y configuración en `/configuracion`.

En S14, `pedido-service` combina circuit breaker, reintentos y bulkhead al consultar servicios remotos. Para simular lentitud, configura `SIMULACION_CLIENTE_MS=5000` y `SIMULACION_PRODUCTO_MS=10000`; por defecto ambos demoran cero. Los servicios distribuidos conservan los datos de demostración en memoria.
