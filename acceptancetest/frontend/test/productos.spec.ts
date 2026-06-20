import { test, expect } from '@playwright/test';

test.describe('Módulo Productos', () => {
  test.beforeEach(async ({ page }) => {
    // Escuchar logs de consola del navegador
    page.on('console', msg => console.log('BROWSER LOG [Productos]:', msg.text()));
    page.on('pageerror', err => console.error('BROWSER ERROR [Productos]:', err.message));

    // 1. Iniciar sesión
    await page.goto('/login');
    await page.locator('input[formControlName="username"]').fill('emp01');
    await page.locator('input[formControlName="password"]').fill('123');
    await page.locator('input[type="submit"]').click();
    await page.waitForURL('**/resume/home');
    
    // 2. Navegar a Productos
    await page.goto('/resume/productos');
    await expect(page).toHaveURL(/.*productos/);
  });

  test('Debería crear un producto, buscarlo en la tabla, y luego eliminarlo', async ({ page }) => {
    const randomNum = Math.floor(1000 + Math.random() * 9000);
    const productName = `Playwright Product ${randomNum}`;
    const productPrice = '45.99';
    const productStock = '150';
    
    // 1. Rellenar formulario de producto
    await page.locator('input[formControlName="nombres"]').fill(productName);
    await page.locator('input[formControlName="precio"]').fill(productPrice);
    await page.locator('input[formControlName="stock"]').fill(productStock);
    await page.locator('input[formControlName="estado"]').fill('1');
    
    // 2. Hacer clic en Aceptar para guardar
    await page.getByRole('button', { name: 'Aceptar' }).click();
    
    // 3. Buscar el producto en la tabla
    await page.locator('input[placeholder="Buscar"]').fill(productName);
    
    // Esperar a que la fila esté visible en la tabla
    const tableRow = page.locator('p-table tbody tr', { hasText: productName });
    await expect(tableRow).toBeVisible();
    await expect(tableRow).toContainText(productPrice);
    await expect(tableRow).toContainText(productStock);
    
    // 4. Eliminar el producto creado para dejar limpia la base de datos
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
