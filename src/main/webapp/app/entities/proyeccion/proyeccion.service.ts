import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProyeccion } from 'app/shared/model/proyeccion.model';

type EntityResponseType = HttpResponse<IProyeccion>;
type EntityArrayResponseType = HttpResponse<IProyeccion[]>;

@Injectable({ providedIn: 'root' })
export class ProyeccionService {
  public resourceUrl = SERVER_API_URL + 'api/proyeccions';

  constructor(protected http: HttpClient) {}

  create(proyeccion: IProyeccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyeccion);
    return this.http
      .post<IProyeccion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proyeccion: IProyeccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyeccion);
    return this.http
      .put<IProyeccion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProyeccion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProyeccion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proyeccion: IProyeccion): IProyeccion {
    const copy: IProyeccion = Object.assign({}, proyeccion, {
      horaProyeccion: proyeccion.horaProyeccion && proyeccion.horaProyeccion.isValid() ? proyeccion.horaProyeccion.toJSON() : undefined,
      fechaComienzo:
        proyeccion.fechaComienzo && proyeccion.fechaComienzo.isValid() ? proyeccion.fechaComienzo.format(DATE_FORMAT) : undefined,
      fechaFinalizacion:
        proyeccion.fechaFinalizacion && proyeccion.fechaFinalizacion.isValid()
          ? proyeccion.fechaFinalizacion.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.horaProyeccion = res.body.horaProyeccion ? moment(res.body.horaProyeccion) : undefined;
      res.body.fechaComienzo = res.body.fechaComienzo ? moment(res.body.fechaComienzo) : undefined;
      res.body.fechaFinalizacion = res.body.fechaFinalizacion ? moment(res.body.fechaFinalizacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proyeccion: IProyeccion) => {
        proyeccion.horaProyeccion = proyeccion.horaProyeccion ? moment(proyeccion.horaProyeccion) : undefined;
        proyeccion.fechaComienzo = proyeccion.fechaComienzo ? moment(proyeccion.fechaComienzo) : undefined;
        proyeccion.fechaFinalizacion = proyeccion.fechaFinalizacion ? moment(proyeccion.fechaFinalizacion) : undefined;
      });
    }
    return res;
  }
}
