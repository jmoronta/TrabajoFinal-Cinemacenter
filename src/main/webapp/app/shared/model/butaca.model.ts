import { Moment } from 'moment';
import { IProyeccion } from 'app/shared/model/proyeccion.model';

export interface IButaca {
  id?: number;
  fechaVenta?: Moment;
  fila?: number;
  asiento?: number;
  estado?: boolean;
  proyeccion?: IProyeccion;
}

export class Butaca implements IButaca {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public fila?: number,
    public asiento?: number,
    public estado?: boolean,
    public proyeccion?: IProyeccion
  ) {
    this.estado = this.estado || false;
  }
}
