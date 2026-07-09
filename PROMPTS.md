Herramienta de IA usada: Claude

PROMPTS:
- Genera la implementacion base para un proyecto de gestión de reservas de hotel, incluyendo controladores, servicios, repositorios y entidades tomando en cuenta los siguiente:
- **Hotel/Sucursal** (el sistema maneja más de una sucursal)
- **Habitación** (pertenece a una sucursal, tiene tipo, precio y disponibilidad)
- **Reserva** (asociada a un huésped, una habitación y un rango de fechas)
- **Usuario** (con rol asignado)

Respuesta:
Aquí tienes la estructura base para el sistema de gestión de reservas. Utilizaremos Spring Boot con Spring Data JPA y Lombok para reducir el código repetitivo, manteniendo la arquitectura en capas (Entidades, Repositorios, Servicios y Controladores).
He estructurado las relaciones para que una Hotel (Hotel) tenga múltiples Habitaciones (Rooms), y una Reserva (Reservation) conecte a un Usuario (User) con una Habitación.

- Genera la implementacion de SecurityConfig para manejar la autenticación y autorización de usuarios, incluyendo la configuración de JWT y roles.
  Para completar la arquitectura de seguridad de tu hotel, necesitas la clase SecurityConfig. Dado que estás usando jakarta.servlet (lo que indica Spring Boot 3 y Spring Security 6+), la configuración debe hacerse utilizando el estilo funcional (lambdas), ya que los métodos antiguos como .antMatchers() o .csrf().disable() están deprecados.

Aquí tienes la implementación completa y optimizada de la configuración de seguridad, aplicando las restricciones de acceso basadas en los roles que definimos antes (ADMIN, RECEPTIONIST, GUEST).
-Maneja las excepciones necesarias 