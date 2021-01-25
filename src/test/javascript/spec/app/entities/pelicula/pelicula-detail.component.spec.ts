import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CinemacenterTestModule } from '../../../test.module';
import { PeliculaDetailComponent } from 'app/entities/pelicula/pelicula-detail.component';
import { Pelicula } from 'app/shared/model/pelicula.model';

describe('Component Tests', () => {
  describe('Pelicula Management Detail Component', () => {
    let comp: PeliculaDetailComponent;
    let fixture: ComponentFixture<PeliculaDetailComponent>;
    const route = ({ data: of({ pelicula: new Pelicula(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CinemacenterTestModule],
        declarations: [PeliculaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PeliculaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeliculaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pelicula on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pelicula).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
