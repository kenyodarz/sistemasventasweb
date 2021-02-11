// Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// Servicio Generico
import { CommonService } from './common.service';
// Variable de Entorno
import { API_URL } from "src/environments/environment";
// Modelo
import { Cliente } from "src/app/core/models/cliente";
@Injectable({
  providedIn: 'root'
})
export class ClienteService extends CommonService<Cliente, number> {
  protected URL_API: string = `${API_URL}/clientes`
  constructor(protected http: HttpClient) { super(http) }
}
