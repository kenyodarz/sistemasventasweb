Feature: API Empleados

  Background:
    * url baseURL
    * def randomDni = function(){ return '' + Math.floor(10000000 + Math.random() * 90000000) }
    * def randomUser = function(){ return 'usr' + Math.floor(10000 + Math.random() * 90000) }

  Scenario: CRUD completo de un Empleado (Crear, Listar, Obtener, Validar, Eliminar)
    # 1. Crear un empleado
    * def dni = randomDni()
    * def username = randomUser()
    Given path '/empleados/save'
    And request { dni: '#(dni)', nombres: 'Empleado Karate', telefono: '555-1234', estado: '1', user: '#(username)' }
    When method POST
    Then status 201
    And match response.idEmpleado == '#present'
    And match response.dni == dni
    * def idEmpleado = response.idEmpleado

    # 2. Consultar todos los empleados
    Given path '/empleados/all'
    When method GET
    Then status 200
    And match response == '#[]'
    And match response[*].idEmpleado contains idEmpleado

    # 3. Consultar el empleado por ID
    Given path '/empleados/' + idEmpleado
    When method GET
    Then status 200
    And match response.idEmpleado == idEmpleado
    And match response.dni == dni

    # 4. Validar empleado por usuario y DNI
    Given path '/empleados/validar/' + username + '/' + dni
    When method GET
    Then status 200
    And match response.idEmpleado == idEmpleado

    # 5. Eliminar el empleado
    Given path '/empleados/delete/' + idEmpleado
    When method GET
    Then status 200
    And match response.idEmpleado == idEmpleado

    # 6. Verificar que ya no existe (Debe retornar 404)
    Given path '/empleados/' + idEmpleado
    When method GET
    Then status 404
