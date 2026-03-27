# DOSW-Library

Sistema de gestión de biblioteca desarrollado con Spring Boot. Permite administrar libros, usuarios y préstamos. Cada usuario puede tener máximo un préstamo activo a la vez, y el sistema verifica automáticamente la disponibilidad del libro antes de realizar el préstamo.

---

## Pruebas de los servicios

Se implementaron pruebas unitarias y funcionales sobre los tres servicios principales. Todas pasan sin errores las 31 pruebas hechas.


---

## Cobertura de código - JaCoCo

Los servicios de negocio alcanzan un 97% de cobertura y los validadores un 86%. El porcentaje total se ve afectado por clases de datos como DTOs y mappers que no contienen lógica.

![JACOCO](docs/images/JACOCO.png)

---

## Análisis estático - SonarQube

El análisis arrojó 0 bugs, 0 vulnerabilidades y 0 security hotspots. Los code smells encontrados son menores y están relacionados principalmente con convenciones de estilo.

![PRUEBAS](docs/images/PUEBAS.png)

---

## Diagrama de clases

El diagrama está organizado en tres capas principales. La primera son los modelos, que representan las entidades del sistema: `Book` con título, autor, ID y disponibilidad; `User` con nombre e ID; y `Loan` que conecta un libro con un usuario guardando la fecha y estado del préstamo.

La segunda capa son los servicios, donde vive toda la lógica de negocio. `LoanService` depende de `BookService` y `UserService` porque al crear un préstamo necesita verificar que el usuario existe y que el libro está disponible.

La tercera capa son los controllers, que reciben las peticiones HTTP y las delegan a sus servicios. Cada controller usa un mapper para convertir entre DTOs y modelos.

![Clases](docs/images/Clases.drawio.png)

## Diagrama de específicos

Aquí veremos a nivel interno el manejo de la estructura del código, con los controladores para los componentes importantes de libros, usuarios y préstamos, con sus mappers que serán los que internamente harán las transformaciones necesarias para que el servicio maneje sus validadores y en un futuro el repositorio se encargará de persistir la información en la base de datos.

![Especifo](docs/images/Especificos.drawio.png)

## Diagrama de componentes general

Aqui en general se coloco lo general del comportamiento de Dosw-Library, con el usuario que entra al front y como tal el back o el core de la api es la que guardara esa solicitudes o operaciones CRUD para que se guarde enn una base de datos.

![GENERAL](docs/images/General.drawio.png)

## Diagrama modelo de entidad relación

En el diagrama podemos visualizar las 3 tablas claves que seran las de libros, usuarios y prestamos con sus respectivos atributos para que en la API en formato JSON se guarde de manera adecuada

![Relacional](docs/images/ModeloRelacional.png)

## Video de las pruebas API

[Link del video de las pruebas API de la funcionalidades](https://youtu.be/EQ_w8G26Qlo)