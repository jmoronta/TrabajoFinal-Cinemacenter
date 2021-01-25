import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPelicula, Pelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from './pelicula.service';
import { PeliculaComponent } from './pelicula.component';
import { PeliculaDetailComponent } from './pelicula-detail.component';
import { PeliculaUpdateComponent } from './pelicula-update.component';

@Injectable({ providedIn: 'root' })
export class PeliculaResolve implements Resolve<IPelicula> {
  constructor(private service: PeliculaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPelicula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pelicula: HttpResponse<Pelicula>) => {
          if (pelicula.body) {
            return of(pelicula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pelicula());
  }
}

export const peliculaRoute: Routes = [
  {
    path: '',
    component: PeliculaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cinemacenterApp.pelicula.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeliculaDetailComponent,
    resolve: {
      pelicula: PeliculaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cinemacenterApp.pelicula.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeliculaUpdateComponent,
    resolve: {
      pelicula: PeliculaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cinemacenterApp.pelicula.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeliculaUpdateComponent,
    resolve: {
      pelicula: PeliculaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cinemacenterApp.pelicula.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
