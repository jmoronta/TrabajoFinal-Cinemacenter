import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CinemacenterSharedModule } from 'app/shared/shared.module';
import { SalaComponent } from './sala.component';
import { SalaDetailComponent } from './sala-detail.component';
import { SalaUpdateComponent } from './sala-update.component';
import { SalaDeleteDialogComponent } from './sala-delete-dialog.component';
import { salaRoute } from './sala.route';

@NgModule({
  imports: [CinemacenterSharedModule, RouterModule.forChild(salaRoute)],
  declarations: [SalaComponent, SalaDetailComponent, SalaUpdateComponent, SalaDeleteDialogComponent],
  entryComponents: [SalaDeleteDialogComponent],
})
export class CinemacenterSalaModule {}
