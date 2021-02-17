// Angular
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// Servicio Generico
import { CommonService } from './common.service';
// Variable de Entorno
import { API_URL } from "src/environments/environment";
// Modelo
import { Producto } from "src/app/core/models/producto";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductoService extends CommonService<Producto, number> {
  protected URL_API: string = `${API_URL}/productos`;
  constructor(protected http: HttpClient) {
    super(http);
  }

  obtenerProductoConStock(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.URL_API}/with-stock/${id}`);
  }

  actualizarStock(id: number, cantidad: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.URL_API}/actualizar/${id}/${cantidad}`)
  }
}
