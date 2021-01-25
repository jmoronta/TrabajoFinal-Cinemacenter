import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IButaca } from 'app/shared/model/butaca.model';

type EntityResponseType = HttpResponse<IButaca>;
type EntityArrayResponseType = HttpResponse<IButaca[]>;

@Injectable({ providedIn: 'root' })
export class ButacaService {
  public resourceUrl = SERVER_API_URL + 'api/butacas';

  constructor(protected http: HttpClient) {}

  create(butaca: IButaca): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(butaca);
    return this.http
      .post<IButaca>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(butaca: IButaca): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(butaca);
    return this.http
      .put<IButaca>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IButaca>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IButaca[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(butaca: IButaca): IButaca {
    const copy: IButaca = Object.assign({}, butaca, {
      fechaVenta: butaca.fechaVenta && butaca.fechaVenta.isValid() ? butaca.fechaVenta.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaVenta = res.body.fechaVenta ? moment(res.body.fechaVenta) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((butaca: IButaca) => {
        butaca.fechaVenta = butaca.fechaVenta ? moment(butaca.fechaVenta) : undefined;
      });
    }
    return res;
  }
}
