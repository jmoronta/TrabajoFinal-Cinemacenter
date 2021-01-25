import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPelicula, Pelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from './pelicula.service';

@Component({
  selector: 'jhi-pelicula-update',
  templateUrl: './pelicula-update.component.html',
})
export class PeliculaUpdateComponent implements OnInit {
  isSaving = false;
  fechaComienzoDp: any;
  fechaFinalizacionDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    duracion: [],
    descripcion: [],
    detalle: [],
    genero: [],
    clasifiacion: [],
    estado: [],
    fechaComienzo: [],
    fechaFinalizacion: [],
  });

  constructor(protected peliculaService: PeliculaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pelicula }) => {
      this.updateForm(pelicula);
    });
  }

  updateForm(pelicula: IPelicula): void {
    this.editForm.patchValue({
      id: pelicula.id,
      nombre: pelicula.nombre,
      duracion: pelicula.duracion,
      descripcion: pelicula.descripcion,
      detalle: pelicula.detalle,
      genero: pelicula.genero,
      clasifiacion: pelicula.clasifiacion,
      estado: pelicula.estado,
      fechaComienzo: pelicula.fechaComienzo,
      fechaFinalizacion: pelicula.fechaFinalizacion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pelicula = this.createFromForm();
    if (pelicula.id !== undefined) {
      this.subscribeToSaveResponse(this.peliculaService.update(pelicula));
    } else {
      this.subscribeToSaveResponse(this.peliculaService.create(pelicula));
    }
  }

  private createFromForm(): IPelicula {
    return {
      ...new Pelicula(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      duracion: this.editForm.get(['duracion'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      detalle: this.editForm.get(['detalle'])!.value,
      genero: this.editForm.get(['genero'])!.value,
      clasifiacion: this.editForm.get(['clasifiacion'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      fechaComienzo: this.editForm.get(['fechaComienzo'])!.value,
      fechaFinalizacion: this.editForm.get(['fechaFinalizacion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPelicula>>): void {
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
}
