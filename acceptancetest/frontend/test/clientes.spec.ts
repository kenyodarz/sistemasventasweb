import { test, expect } from '@playwright/test';

test.describe('Módulo Clientes', () => {
  test.beforeEach(async ({ page }) => {
    // Escuchar logs de consola del navegador
    page.on('console', msg => console.log('BROWSER LOG [Clientes]:', msg.text()));
    page.on('pageerror', err => console.error('BROWSER ERROR [Clientes]:', err.message));

    // 1. Iniciar sesión
    await page.goto('/login');
    await page.locator('input[formControlName="username"]').fill('emp01');
    await page.locator('input[formControlName="password"]').fill('123');
    await page.locator('input[type="submit"]').click();
    await page.waitForURL('**/resume/home');
    
    // 2. Navegar a Clientes
    await page.goto('/resume/clientes');
    await expect(page).toHaveURL(/.*clientes/);
  });

  test('Debería crear un cliente, buscarlo en la tabla, y luego eliminarlo', async ({ page }) => {
    const randomNum = Math.floor(10000000 + Math.random() * 90000000);
    const clientDni = `${randomNum}`;
    const clientName = `Playwright Client ${randomNum}`;
    
    // 1. Rellenar formulario de cliente
    await page.locator('input[formControlName="dni"]').fill(clientDni);
    await page.locator('input[formControlName="nombres"]').fill(clientName);
    await page.locator('input[formControlName="direccion"]').fill('Calle Playwright 456');
    await page.locator('input[formControlName="estado"]').fill('1');
    
    // 2. Hacer clic en Aceptar para guardar
    await page.getByRole('button', { name: 'Aceptar' }).click();
    
    // 3. Buscar el cliente en la tabla
    await page.locator('input[placeholder="Buscar"]').fill(clientDni);
    
    // Esperar a que la fila esté visible en la tabla
    const tableRow = page.locator('p-table tbody tr', { hasText: clientDni });
    await expect(tableRow).toBeVisible();
    await expect(tableRow).toContainText(clientName);
    
    // 4. Eliminar el cliente creado para dejar limpia la base de datos
    await tableRow.locator('button.p-button-danger').click();
    
    // Hacer clic en "Si" en el diálogo de confirmación de PrimeNG
    const acceptButton = page.getByRole('button', { name: 'Si' });
    await expect(acceptButton).toBeVisible();
    await acceptButton.click();
    
    // Limpiar el buscador para forzar a la tabla a refrescar la lista completa
    await page.locator('input[placeholder="Buscar"]').fill('');
    
    // Verificar que la fila se eliminó
    await expect(tableRow).not.toBeVisible();
  });
});
