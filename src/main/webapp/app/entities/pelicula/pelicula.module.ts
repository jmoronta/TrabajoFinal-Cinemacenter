import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CinemacenterSharedModule } from 'app/shared/shared.module';
import { PeliculaComponent } from './pelicula.component';
import { PeliculaDetailComponent } from './pelicula-detail.component';
import { PeliculaUpdateComponent } from './pelicula-update.component';
import { PeliculaDeleteDialogComponent } from './pelicula-delete-dialog.component';
import { peliculaRoute } from './pelicula.route';

@NgModule({
  imports: [CinemacenterSharedModule, RouterModule.forChild(peliculaRoute)],
  declarations: [PeliculaComponent, PeliculaDetailComponent, PeliculaUpdateComponent, PeliculaDeleteDialogComponent],
  entryComponents: [PeliculaDeleteDialogComponent],
})
export class CinemacenterPeliculaModule {}
