import { Moment } from 'moment';
import { IButaca } from 'app/shared/model/butaca.model';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { IPelicula } from 'app/shared/model/pelicula.model';

export interface IVenta {
  id?: number;
  fechaVenta?: Moment;
  butaca?: IButaca;
  proyeccion?: IProyeccion;
  pelicula?: IPelicula;
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public butaca?: IButaca,
    public proyeccion?: IProyeccion,
    public pelicula?: IPelicula
  ) {}
}
