import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CinemacenterTestModule } from '../../../test.module';
import { ButacaDetailComponent } from 'app/entities/butaca/butaca-detail.component';
import { Butaca } from 'app/shared/model/butaca.model';

describe('Component Tests', () => {
  describe('Butaca Management Detail Component', () => {
    let comp: ButacaDetailComponent;
    let fixture: ComponentFixture<ButacaDetailComponent>;
    const route = ({ data: of({ butaca: new Butaca(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CinemacenterTestModule],
        declarations: [ButacaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ButacaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ButacaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load butaca on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.butaca).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
