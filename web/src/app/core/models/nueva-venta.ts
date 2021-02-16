import { Producto } from "./producto";

export class NuevaVenta {
  constructor(
    public id: number = null,
    public idCliente: number = null,
    public idEmpleado: number = null,
    public idProducto: Producto = null,
    public numeroSerie: string = null,
    public descriptionP: string = null,
    public fecha: string = null,
    public precio: number = null,
    public cantidad: number = null,
    public subtotal: number = null,
    public monto: number = null,
    public estado: string = null
  ) {}
}
