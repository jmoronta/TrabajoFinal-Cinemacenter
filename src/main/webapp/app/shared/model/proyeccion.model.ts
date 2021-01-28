import { Moment } from 'moment';
import { IButaca } from 'app/shared/model/butaca.model';
import { IVenta } from 'app/shared/model/venta.model';
import { IPelicula } from 'app/shared/model/pelicula.model';
import { ISala } from 'app/shared/model/sala.model';

export interface IProyeccion {
  id?: number;
  horaProyeccion?: Moment;
  estado?: boolean;
  fechaComienzo?: Moment;
  fechaFinalizacion?: Moment;
  butacas?: IButaca[];
  ventas?: IVenta[];
  pelicula?: IPelicula;
  sala?: ISala;
}

export class Proyeccion implements IProyeccion {
  constructor(
    public id?: number,
    public horaProyeccion?: Moment,
    public estado?: boolean,
    public fechaComienzo?: Moment,
    public fechaFinalizacion?: Moment,
    public butacas?: IButaca[],
    public ventas?: IVenta[],
    public pelicula?: IPelicula,
    public sala?: ISala
  ) {
    this.estado = this.estado || false;
  }
}
