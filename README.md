# g00-prueba

Repositorio de prueba del pipeline DevSecOps del Taller de Proyectos.
Aplicación Spring Boot mínima (Java 21, Maven) conectada a
`Taller-de-proyectos-2027-I/devsecops-templates@v1`.

- `GET /api/saludo?nombre=Ana` → saludo
- `GET /actuator/health` → estado
- `GET /v3/api-docs` → especificación OpenAPI (usada por el DAST)

El despliegue a AWS se activa solo cuando el repositorio tiene la variable `AWS_ROLE_ARN`.
