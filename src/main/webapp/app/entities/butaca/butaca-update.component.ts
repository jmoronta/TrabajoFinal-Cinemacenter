import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IButaca, Butaca } from 'app/shared/model/butaca.model';
import { ButacaService } from './butaca.service';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from 'app/entities/proyeccion/proyeccion.service';
import { ISala } from 'app/shared/model/sala.model';
import { SalaService } from 'app/entities/sala/sala.service';

type SelectableEntity = IProyeccion | ISala;

@Component({
  selector: 'jhi-butaca-update',
  templateUrl: './butaca-update.component.html',
})
export class ButacaUpdateComponent implements OnInit {
  isSaving = false;
  proyeccions: IProyeccion[] = [];
  salas: ISala[] = [];
  fechaVentaDp: any;

  editForm = this.fb.group({
    id: [],
    fechaVenta: [],
    fila: [],
    asiento: [],
    estado: [],
    proyeccion: [],
    sala: [],
  });

  constructor(
    protected butacaService: ButacaService,
    protected proyeccionService: ProyeccionService,
    protected salaService: SalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ butaca }) => {
      this.updateForm(butaca);

      this.proyeccionService.query().subscribe((res: HttpResponse<IProyeccion[]>) => (this.proyeccions = res.body || []));

      this.salaService.query().subscribe((res: HttpResponse<ISala[]>) => (this.salas = res.body || []));
    });
  }

  updateForm(butaca: IButaca): void {
    this.editForm.patchValue({
      id: butaca.id,
      fechaVenta: butaca.fechaVenta,
      fila: butaca.fila,
      asiento: butaca.asiento,
      estado: butaca.estado,
      proyeccion: butaca.proyeccion,
      sala: butaca.sala,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const butaca = this.createFromForm();
    if (butaca.id !== undefined) {
      this.subscribeToSaveResponse(this.butacaService.update(butaca));
    } else {
      this.subscribeToSaveResponse(this.butacaService.create(butaca));
    }
  }

  private createFromForm(): IButaca {
    return {
      ...new Butaca(),
      id: this.editForm.get(['id'])!.value,
      fechaVenta: this.editForm.get(['fechaVenta'])!.value,
      fila: this.editForm.get(['fila'])!.value,
      asiento: this.editForm.get(['asiento'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      proyeccion: this.editForm.get(['proyeccion'])!.value,
      sala: this.editForm.get(['sala'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IButaca>>): void {
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
