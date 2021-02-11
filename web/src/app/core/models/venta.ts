import { Cliente } from './cliente';
import { Empleado } from './empleado';

export class Venta {
  constructor(
    public idVenta: number = null,
    public cliente: Cliente = null,
    public empleado: Empleado = null,
    public numeroSerie: string = null,
    public fechaVentas: Date = null,
    public monto: number = null,
    public estado: string = null
  ) {}
}
