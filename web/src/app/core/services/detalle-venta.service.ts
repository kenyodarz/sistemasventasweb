// Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// Servicio Generico
import { CommonService } from './common.service';
// Variable de Entorno
import { API_URL } from "src/environments/environment";
// Modelo
import { DetalleVenta } from "src/app/core/models/detalle-venta";

@Injectable({
  providedIn: 'root',
})
export class DetalleVentaService extends CommonService<DetalleVenta, number> {
  protected URL_API: string = `${API_URL}/detalle`;
  constructor(protected http: HttpClient) { super(http) }
}
