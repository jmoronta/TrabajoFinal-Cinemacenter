import { Moment } from 'moment';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IPelicula {
  id?: number;
  nombre?: string;
  duracion?: number;
  descripcion?: string;
  detalle?: string;
  genero?: string;
  clasifiacion?: string;
  estado?: boolean;
  fechaComienzo?: Moment;
  fechaFinalizacion?: Moment;
  proyeccions?: IProyeccion[];
  ventas?: IVenta[];
}

export class Pelicula implements IPelicula {
  constructor(
    public id?: number,
    public nombre?: string,
    public duracion?: number,
    public descripcion?: string,
    public detalle?: string,
    public genero?: string,
    public clasifiacion?: string,
    public estado?: boolean,
    public fechaComienzo?: Moment,
    public fechaFinalizacion?: Moment,
    public proyeccions?: IProyeccion[],
    public ventas?: IVenta[]
  ) {
    this.estado = this.estado || false;
  }
}
