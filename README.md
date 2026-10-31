# g00-prueba

Repositorio de prueba del pipeline DevSecOps del Taller de Proyectos.
Aplicación Spring Boot mínima (Java 21, Maven) conectada a
`Taller-de-proyectos-2027-I/devsecops-templates@v1`.

- `GET /api/saludo?nombre=Ana` → saludo
- `GET /actuator/health` → estado
- `GET /v3/api-docs` → especificación OpenAPI (usada por el DAST)

El despliegue a AWS se activa solo cuando el repositorio tiene la variable `AWS_ROLE_ARN`.

## Configuración AWS (modo `usuario_iam`)
- Región: **us-east-2 (Ohio)**.
- Variables del repo: `AWS_ROLE_ARN`, `TF_STATE_BUCKET`, `FINDINGS_BUCKET`.
- Llaves de `devsecops-pipeline` solo en los environments `staging` y `aprobacion-release` (limitados a `main`).
- Cada push a `main` construye la imagen, la publica en ECR (`taller/g00`), levanta un staging efímero en EC2, ejecuta OWASP ZAP y lo destruye.
