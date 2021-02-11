// Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// Servicio Generico
import { CommonService } from './common.service';
// Variable de Entorno
import { API_URL } from "src/environments/environment";
// Modelo
import { Empleado } from "src/app/core/models/empleado";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmpleadoService extends CommonService<Empleado, number>{
  protected URL_API: string = `${API_URL}/empleados`;
  constructor(protected http: HttpClient) { super(http); }

  validarEmpleado(credenciales: any):Observable<Empleado>{
    return this.http.get<Empleado>(
      `${this.URL_API}/validar/${credenciales.username}/${credenciales.password}`
    );
  }
}
