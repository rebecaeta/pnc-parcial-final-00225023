### Reflexion sobre el uso de la IA en el proyecto

- ¿Qué partes generó bien la IA sin necesidad de corrección?
Generalmente la estructura básica de los controladores, entidades y servicios. 
La IA fue capaz de generar código funcional para las operaciones CRUD, así como la configuración inicial de Docker y GitHub Actions.

- ¿Qué errores o decisiones incorrectas tomó la IA, especialmente en temas de seguridad?
En algunas ocasiones, la IA generó código que no implementaba correctamente la autorización por sucursal, 
permitiendo que usuarios de una sucursal accedieran a datos de otra. 
También hubo casos donde la validación no era implementada completamente.

- ¿Cómo detectaron esos errores y cómo los corrigieron?
Los errores fueron detectados mediante pruebas por cada funcionalidad, revisando que era lo que devolvian los endpoints y si lo manejaba bien el codigo como la base de datos.

- Si tuvieran que explicarle a un compañero cómo funciona el mecanismo de autorización por sucursal (o la regla de negocio que eligieron), ¿qué le dirían en 3-4 líneas?
Aplique la regla de negocio de invalidacion de tokens por cambio de contraseña, de manera que si un usuario cambia su contraseña, 
todos los tokens anteriormente utilizados se invalidan. Asi el usuario tenga que autenticarse nuevamente para obtener un nuevo token y mantener seguridad