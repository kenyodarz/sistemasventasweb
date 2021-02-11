import { Producto } from "./producto";
import { Venta } from "./venta";

export class DetalleVenta {
  constructor(
    public idDetalleVenta: number = null,
    public venta: Venta = null,
    public producto: Producto = null,
    public cantidad: number = null,
    public precioVenta: number = null
  ) {}
}
