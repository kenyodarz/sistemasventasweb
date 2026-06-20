Feature: API Ventas y Detalle de Ventas

  Background:
    * url baseURL
    * def randomDni = function(){ return '' + Math.floor(10000000 + Math.random() * 90000000) }
    * def randomUser = function(){ return 'usr' + Math.floor(10000 + Math.random() * 90000) }

  Scenario: CRUD completo de una Venta y Detalle de Venta
    # 1. Crear Cliente temporal
    * def clientDni = randomDni()
    Given path '/clientes/save'
    And request { dni: '#(clientDni)', nombres: 'Cliente Venta', direccion: 'Calle Venta 123', estado: '1' }
    When method POST
    Then status 201
    * def cliente = response

    # 2. Crear Empleado temporal
    * def empDni = randomDni()
    * def empUser = randomUser()
    Given path '/empleados/save'
    And request { dni: '#(empDni)', nombres: 'Empleado Venta', telefono: '555-1234', estado: '1', user: '#(empUser)' }
    When method POST
    Then status 201
    * def empleado = response

    # 3. Crear Producto temporal
    Given path '/productos/save'
    And request { nombres: 'Producto Venta', precio: 50.0, stock: 10, estado: '1' }
    When method POST
    Then status 201
    * def producto = response

    # 4. Obtener Número de Serie
    Given path '/ventas/serie'
    When method GET
    Then status 200
    * def numeroSerie = response.numero

    # 5. Crear Venta
    Given path '/ventas/save'
    And request { cliente: '#(cliente)', empleado: '#(empleado)', numeroSerie: '#(numeroSerie)', monto: 100.0, estado: '1' }
    When method POST
    Then status 201
    * def venta = response
    * def idVenta = response.idVenta

    # 6. Crear Detalle de Venta
    Given path '/detalle/save'
    And request { venta: '#(venta)', producto: '#(producto)', cantidad: 2, precioVenta: 50.0 }
    When method POST
    Then status 201
    * def idDetalle = response.idDetalleVenta

    # 7. Consultar Venta por ID
    Given path '/ventas/' + idVenta
    When method GET
    Then status 200
    And match response.idVenta == idVenta

    # 8. Eliminar Detalle de Venta
    Given path '/detalle/delete/' + idDetalle
    When method GET
    Then status 200

    # 9. Eliminar Venta
    Given path '/ventas/delete/' + idVenta
    When method GET
    Then status 200

    # 10. Eliminar Producto temporal
    Given path '/productos/delete/' + producto.idProducto
    When method GET
    Then status 200

    # 11. Eliminar Empleado temporal
    Given path '/empleados/delete/' + empleado.idEmpleado
    When method GET
    Then status 200

    # 12. Eliminar Cliente temporal
    Given path '/clientes/delete/' + cliente.idCliente
    When method GET
    Then status 200
