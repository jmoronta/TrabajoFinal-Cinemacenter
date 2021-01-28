import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { IButaca } from 'app/shared/model/butaca.model';

export interface ISala {
  id?: number;
  nombre?: string;
  estado?: number;
  filas?: number;
  asientos?: number;
  proyeccions?: IProyeccion[];
  butacas?: IButaca[];
}

export class Sala implements ISala {
  constructor(
    public id?: number,
    public nombre?: string,
    public estado?: number,
    public filas?: number,
    public asientos?: number,
    public proyeccions?: IProyeccion[],
    public butacas?: IButaca[]
  ) {}
}
