import { Moment } from 'moment';
import { IVenta } from 'app/shared/model/venta.model';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { ISala } from 'app/shared/model/sala.model';

export interface IButaca {
  id?: number;
  fechaVenta?: Moment;
  fila?: number;
  asiento?: number;
  estado?: boolean;
  ventas?: IVenta[];
  proyeccion?: IProyeccion;
  sala?: ISala;
}

export class Butaca implements IButaca {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public fila?: number,
    public asiento?: number,
    public estado?: boolean,
    public ventas?: IVenta[],
    public proyeccion?: IProyeccion,
    public sala?: ISala
  ) {
    this.estado = this.estado || false;
  }
}
