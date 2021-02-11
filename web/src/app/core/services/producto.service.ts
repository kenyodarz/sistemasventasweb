// Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// Servicio Generico
import { CommonService } from './common.service';
// Variable de Entorno
import { API_URL } from "src/environments/environment";
// Modelo
import { Producto } from "src/app/core/models/producto";

@Injectable({
  providedIn: 'root',
})
export class ProductoService extends CommonService<Producto, number>{
  protected URL_API: string = `${API_URL}/productos`;
  constructor(protected http: HttpClient) { super(http); }
}
