import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ButacaService } from 'app/entities/butaca/butaca.service';
import { IButaca, Butaca } from 'app/shared/model/butaca.model';

describe('Service Tests', () => {
  describe('Butaca Service', () => {
    let injector: TestBed;
    let service: ButacaService;
    let httpMock: HttpTestingController;
    let elemDefault: IButaca;
    let expectedResult: IButaca | IButaca[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ButacaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Butaca(0, currentDate, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Butaca', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaVenta: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaVenta: currentDate,
          },
          returnedFromService
        );

        service.create(new Butaca()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Butaca', () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT),
            fila: 1,
            asiento: 1,
            estado: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaVenta: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Butaca', () => {
        const returnedFromService = Object.assign(
          {
            fechaVenta: currentDate.format(DATE_FORMAT),
            fila: 1,
            asiento: 1,
            estado: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaVenta: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Butaca', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
