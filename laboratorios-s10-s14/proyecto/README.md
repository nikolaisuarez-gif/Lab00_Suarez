# Laboratorios de Desarrollo Web Avanzado (S10–S14)

Autor: **Nikolai Alexander Suarez Núñez** · Paquetes Java: `com.suarez`

Proyecto progresivo con cinco carpetas de laboratorio y sus guías originales. Los ejemplos del docente se adaptaron al paquete del estudiante. Requisitos: JDK 17+ y Maven 3.9+.

## Módulos
- `semana10-empleados`: perfiles DEV/QA/TEST/PROD, variables de entorno, CRUD REST y `/configuracion`.
- `semana11-clinica`: APIs REST de pacientes, médicos y citas.
- `eureka-server`, `categoria-service`, `cliente-service`, `producto-service`, `pedido-service`: catálogo y pedidos con Eureka/OpenFeign.
- `gateway-service`: rutas Gateway para categoría, cliente, producto y pedido.
- `config-server`: configuración centralizada por perfil desde `config-repo` local (sin credenciales externas).

## Compilar
Desde esta carpeta: `mvn clean compile -DskipTests`.

## Ejecución sugerida
Inicia cada módulo en una terminal con `mvn -pl <modulo> spring-boot:run`. Orden: Eureka (8761), categoría (8082), cliente (8081), producto (8083), pedido (8084) y Gateway (8080). Config Server (8888) se inicia aparte.

Ejemplo: `POST http://localhost:8081/api/clientes` con `{"nombre":"Nikolai","apellido":"Suarez","direccion":"Lima","nroDni":"12345678","estado":"A"}`. Después, consulta `GET http://localhost:8080/api/pedidos/{id}`. Semana 10: CRUD en `/empleados` y configuración en `/configuracion`.

Los módulos S10/S11 usan almacenamiento temporal en memoria para que la práctica arranque sin instalar MySQL; los datos se borran al detener la app. El laboratorio 14 usa el perfil native local, sin repositorio remoto ni costos de hosting. Para simular lentitud, configura `SIMULACION_CLIENTE_MS=5000` y `SIMULACION_PRODUCTO_MS=10000`; por defecto ambos demoran cero.
