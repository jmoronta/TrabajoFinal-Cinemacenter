import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProyeccion, Proyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from './proyeccion.service';
import { IPelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from 'app/entities/pelicula/pelicula.service';
import { ISala } from 'app/shared/model/sala.model';
import { SalaService } from 'app/entities/sala/sala.service';

type SelectableEntity = IPelicula | ISala;

@Component({
  selector: 'jhi-proyeccion-update',
  templateUrl: './proyeccion-update.component.html',
})
export class ProyeccionUpdateComponent implements OnInit {
  isSaving = false;
  peliculas: IPelicula[] = [];
  salas: ISala[] = [];
  fechaComienzoDp: any;
  fechaFinalizacionDp: any;

  editForm = this.fb.group({
    id: [],
    horaProyeccion: [],
    estado: [],
    fechaComienzo: [],
    fechaFinalizacion: [],
    pelicula: [],
    sala: [],
  });

  constructor(
    protected proyeccionService: ProyeccionService,
    protected peliculaService: PeliculaService,
    protected salaService: SalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proyeccion }) => {
      if (!proyeccion.id) {
        const today = moment().startOf('day');
        proyeccion.horaProyeccion = today;
      }

      this.updateForm(proyeccion);

      this.peliculaService.query().subscribe((res: HttpResponse<IPelicula[]>) => (this.peliculas = res.body || []));

      this.salaService.query().subscribe((res: HttpResponse<ISala[]>) => (this.salas = res.body || []));
    });
  }

  updateForm(proyeccion: IProyeccion): void {
    this.editForm.patchValue({
      id: proyeccion.id,
      horaProyeccion: proyeccion.horaProyeccion ? proyeccion.horaProyeccion.format(DATE_TIME_FORMAT) : null,
      estado: proyeccion.estado,
      fechaComienzo: proyeccion.fechaComienzo,
      fechaFinalizacion: proyeccion.fechaFinalizacion,
      pelicula: proyeccion.pelicula,
      sala: proyeccion.sala,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proyeccion = this.createFromForm();
    if (proyeccion.id !== undefined) {
      this.subscribeToSaveResponse(this.proyeccionService.update(proyeccion));
    } else {
      this.subscribeToSaveResponse(this.proyeccionService.create(proyeccion));
    }
  }

  private createFromForm(): IProyeccion {
    return {
      ...new Proyeccion(),
      id: this.editForm.get(['id'])!.value,
      horaProyeccion: this.editForm.get(['horaProyeccion'])!.value
        ? moment(this.editForm.get(['horaProyeccion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estado: this.editForm.get(['estado'])!.value,
      fechaComienzo: this.editForm.get(['fechaComienzo'])!.value,
      fechaFinalizacion: this.editForm.get(['fechaFinalizacion'])!.value,
      pelicula: this.editForm.get(['pelicula'])!.value,
      sala: this.editForm.get(['sala'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProyeccion>>): void {
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
