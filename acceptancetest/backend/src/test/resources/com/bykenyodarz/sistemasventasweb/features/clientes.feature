Feature: API Clientes

  Background:
    * url baseURL
    * def randomDni = function(){ return '' + Math.floor(10000000 + Math.random() * 90000000) }

  Scenario: CRUD completo de un Cliente (Crear, Listar, Obtener, Buscar por DNI, Eliminar)
    # 1. Crear un cliente
    * def dni = randomDni()
    Given path '/clientes/save'
    And request { dni: '#(dni)', nombres: 'Cliente Karate', direccion: 'Calle Falsa 123', estado: '1' }
    When method POST
    Then status 201
    And match response.idCliente == '#present'
    And match response.dni == dni
    * def idCliente = response.idCliente

    # 2. Consultar todos los clientes
    Given path '/clientes/all'
    When method GET
    Then status 200
    And match response == '#[]'
    And match response[*].idCliente contains idCliente

    # 3. Consultar el cliente por ID
    Given path '/clientes/' + idCliente
    When method GET
    Then status 200
    And match response.idCliente == idCliente
    And match response.dni == dni

    # 4. Buscar cliente por DNI
    Given path '/clientes/dni/' + dni
    When method GET
    Then status 200
    And match response.idCliente == idCliente

    # 5. Eliminar el cliente
    Given path '/clientes/delete/' + idCliente
    When method GET
    Then status 200
    And match response.idCliente == idCliente

    # 6. Verificar que ya no existe (Debe retornar 404)
    Given path '/clientes/' + idCliente
    When method GET
    Then status 404
