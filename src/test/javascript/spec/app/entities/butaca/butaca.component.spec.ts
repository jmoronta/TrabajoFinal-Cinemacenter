import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CinemacenterTestModule } from '../../../test.module';
import { ButacaComponent } from 'app/entities/butaca/butaca.component';
import { ButacaService } from 'app/entities/butaca/butaca.service';
import { Butaca } from 'app/shared/model/butaca.model';

describe('Component Tests', () => {
  describe('Butaca Management Component', () => {
    let comp: ButacaComponent;
    let fixture: ComponentFixture<ButacaComponent>;
    let service: ButacaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CinemacenterTestModule],
        declarations: [ButacaComponent],
      })
        .overrideTemplate(ButacaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ButacaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ButacaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Butaca(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.butacas && comp.butacas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
