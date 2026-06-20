Feature: API Productos

  Background:
    * url baseURL

  Scenario: CRUD completo de un Producto (Crear, Listar, Obtener, Actualizar Stock, Eliminar)
    # 1. Crear un producto
    Given path '/productos/save'
    And request { nombres: 'Producto Karate', precio: 99.99, stock: 100, estado: '1' }
    When method POST
    Then status 201
    And match response.idProducto == '#present'
    And match response.nombres == 'Producto Karate'
    * def idProducto = response.idProducto

    # 2. Consultar todos los productos
    Given path '/productos/all'
    When method GET
    Then status 200
    And match response == '#[]'
    And match response[*].idProducto contains idProducto

    # 3. Consultar el producto por ID
    Given path '/productos/' + idProducto
    When method GET
    Then status 200
    And match response.idProducto == idProducto

    # 4. Obtener producto con stock
    Given path '/productos/with-stock/' + idProducto
    When method GET
    Then status 200
    And match response.stock == 100

    # 5. Actualizar stock (Descontar stock)
    Given path '/productos/actualizar/' + idProducto + '/5'
    When method GET
    Then status 200
    And match response.stock == 95

    # 6. Eliminar el producto
    Given path '/productos/delete/' + idProducto
    When method GET
    Then status 200
    And match response.idProducto == idProducto

    # 7. Verificar que ya no existe (Debe retornar 404)
    Given path '/productos/' + idProducto
    When method GET
    Then status 404
