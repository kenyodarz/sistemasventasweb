# Guía de Ejecución de Pruebas de Aceptación

Esta guía detalla la configuración, preparación del entorno y los pasos necesarios para ejecutar las pruebas de aceptación e integración del sistema de ventas web, tanto para el **Backend** como para el **Frontend**.

---

## 📋 Requisitos Previos Generales

Antes de ejecutar cualquiera de las suites de pruebas, asegúrese de cumplir con lo siguiente en su máquina local:

1. **Servidor Backend Activo**:
   * El servicio de Spring Boot debe estar ejecutándose en `http://localhost:8080`.
   * El puerto 8080 debe estar libre y accesible.

2. **Servidor Frontend Activo**:
   * El servidor de desarrollo de Angular debe estar activo en `http://localhost:4200` (se inicia con `pnpm start` o `npm start` desde el directorio `web/`).

3. **Base de Datos MySQL**:
   * La base de datos local debe estar levantada y con el esquema correspondiente.
   * Las pruebas realizan creación y eliminación automática de registros de prueba (clientes, productos, etc.).

4. **Credenciales del Sistema por Defecto**:
   * Las pruebas de frontend utilizan el usuario `emp01` con la contraseña `123` para el login automatizado.

---

## ☕ 1. Pruebas de Aceptación del Backend (Karate Framework)

Las pruebas del backend están escritas en **Karate Framework** y se ejecutan utilizando **Gradle** como un subproyecto independiente ubicado en `acceptancetest/backend/`.

### Pasos para Ejecutar:

1. Abra una terminal y navegue a la carpeta del subproyecto:
   ```cmd
   cd acceptancetest/backend
   ```

2. Ejecute las pruebas utilizando el Gradle Wrapper local:
   ```cmd
   .\gradlew.bat test
   ```

   > [!NOTE]
   > El proyecto descarga automáticamente la versión de **Gradle 9.5.1** y compila las pruebas utilizando un **Java Toolchain 21** para garantizar compatibilidad con el código local.

3. **Ver Reportes**:
   Una vez finalizadas las pruebas, se generará un reporte HTML interactivo de Cucumber/Karate. Puede visualizarlo abriendo el siguiente archivo en su navegador:
   * **Reporte de Karate**: `acceptancetest/backend/build/karate-reports/karate-summary.html`

---

## 🎭 2. Pruebas de Aceptación del Frontend (Playwright + TypeScript)

Las pruebas del frontend simulan la navegación de usuario en el navegador real. Están escritas en **Playwright** y se gestionan con **pnpm** en un subproyecto independiente en `acceptancetest/frontend/`.

### Pasos para Ejecutar:

1. Abra una terminal y navegue al directorio del frontend:
   ```cmd
   cd acceptancetest/frontend
   ```

2. Instale las dependencias del subproyecto (sólo la primera vez o si cambian):
   ```cmd
   pnpm.cmd install
   ```

3. Descargue e instale el navegador Chromium requerido por Playwright:
   ```cmd
   pnpm.cmd exec playwright install chromium
   ```

4. Ejecute las pruebas de forma silenciosa (Headless):
   ```cmd
   pnpm.cmd test
   ```

5. **Ejecutar en Modo Interactivo (UI de Playwright)**:
   Si desea ver la ejecución en tiempo real en una interfaz gráfica para depurar paso a paso:
   ```cmd
   pnpm.cmd run test:ui
   ```

6. **Ver Reportes**:
   Playwright genera reportes interactivos en caso de éxito o de fallo (incluyendo capturas de pantalla automáticas de los errores):
   * **Para abrir el último reporte**:
     ```cmd
     pnpm.cmd exec playwright show-report
     ```
   * **Ubicación del reporte físico**: `acceptancetest/frontend/playwright-report/index.html`

---

## 🛠️ Solución de Problemas Comunes en Windows

### 1. Error de Política de Ejecución de Scripts en PowerShell (`pnpm.ps1`)
* **Problema**: PowerShell bloquea la ejecución del comando directo `pnpm`.
* **Solución**: Use explícitamente `pnpm.cmd` (ej: `pnpm.cmd test` o `pnpm.cmd install`) para evitar las políticas restrictivas de PowerShell sobre archivos `.ps1`.

### 2. Duplicado o Falla por Concurrencia de Datos en Base de Datos
* **Problema**: Si dos pruebas intentan crear registros con el mismo DNI o ID al mismo tiempo, la base de datos local arrojará errores de restricción única.
* **Solución**:
  * Las suites están configuradas para ejecutarse con **un solo worker de forma secuencial** (`workers: 1` en `playwright.config.ts` y ejecución controlada en Gradle) para evitar colisiones.
  * Si una prueba se interrumpe y deja registros huérfanos, límpielos manualmente de la base de datos o ejecute nuevamente para que las rutinas de borrado lo resuelvan.
