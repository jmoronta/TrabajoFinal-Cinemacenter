import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVenta, Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { IButaca } from 'app/shared/model/butaca.model';
import { ButacaService } from 'app/entities/butaca/butaca.service';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from 'app/entities/proyeccion/proyeccion.service';
import { IPelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from 'app/entities/pelicula/pelicula.service';

type SelectableEntity = IButaca | IProyeccion | IPelicula;

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html',
})
export class VentaUpdateComponent implements OnInit {
  isSaving = false;
  butacas: IButaca[] = [];
  proyeccions: IProyeccion[] = [];
  peliculas: IPelicula[] = [];
  fechaVentaDp: any;

  editForm = this.fb.group({
    id: [],
    fechaVenta: [],
    butaca: [],
    proyeccion: [],
    pelicula: [],
  });

  constructor(
    protected ventaService: VentaService,
    protected butacaService: ButacaService,
    protected proyeccionService: ProyeccionService,
    protected peliculaService: PeliculaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.updateForm(venta);

      this.butacaService.query().subscribe((res: HttpResponse<IButaca[]>) => (this.butacas = res.body || []));

      this.proyeccionService.query().subscribe((res: HttpResponse<IProyeccion[]>) => (this.proyeccions = res.body || []));

      this.peliculaService.query().subscribe((res: HttpResponse<IPelicula[]>) => (this.peliculas = res.body || []));
    });
  }

  updateForm(venta: IVenta): void {
    this.editForm.patchValue({
      id: venta.id,
      fechaVenta: venta.fechaVenta,
      butaca: venta.butaca,
      proyeccion: venta.proyeccion,
      pelicula: venta.pelicula,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  private createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id'])!.value,
      fechaVenta: this.editForm.get(['fechaVenta'])!.value,
      butaca: this.editForm.get(['butaca'])!.value,
      proyeccion: this.editForm.get(['proyeccion'])!.value,
      pelicula: this.editForm.get(['pelicula'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
