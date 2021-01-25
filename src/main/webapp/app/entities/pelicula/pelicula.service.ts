import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPelicula } from 'app/shared/model/pelicula.model';

type EntityResponseType = HttpResponse<IPelicula>;
type EntityArrayResponseType = HttpResponse<IPelicula[]>;

@Injectable({ providedIn: 'root' })
export class PeliculaService {
  public resourceUrl = SERVER_API_URL + 'api/peliculas';

  constructor(protected http: HttpClient) {}

  create(pelicula: IPelicula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pelicula);
    return this.http
      .post<IPelicula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pelicula: IPelicula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pelicula);
    return this.http
      .put<IPelicula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPelicula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPelicula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pelicula: IPelicula): IPelicula {
    const copy: IPelicula = Object.assign({}, pelicula, {
      fechaComienzo: pelicula.fechaComienzo && pelicula.fechaComienzo.isValid() ? pelicula.fechaComienzo.format(DATE_FORMAT) : undefined,
      fechaFinalizacion:
        pelicula.fechaFinalizacion && pelicula.fechaFinalizacion.isValid() ? pelicula.fechaFinalizacion.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaComienzo = res.body.fechaComienzo ? moment(res.body.fechaComienzo) : undefined;
      res.body.fechaFinalizacion = res.body.fechaFinalizacion ? moment(res.body.fechaFinalizacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pelicula: IPelicula) => {
        pelicula.fechaComienzo = pelicula.fechaComienzo ? moment(pelicula.fechaComienzo) : undefined;
        pelicula.fechaFinalizacion = pelicula.fechaFinalizacion ? moment(pelicula.fechaFinalizacion) : undefined;
      });
    }
    return res;
  }
}
