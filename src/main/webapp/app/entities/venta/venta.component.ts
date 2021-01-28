import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { VentaDeleteDialogComponent } from './venta-delete-dialog.component';

@Component({
  selector: 'jhi-venta',
  templateUrl: './venta.component.html',
})
export class VentaComponent implements OnInit, OnDestroy {
  ventas?: IVenta[];
  eventSubscriber?: Subscription;

  constructor(protected ventaService: VentaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ventaService.query().subscribe((res: HttpResponse<IVenta[]>) => (this.ventas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVentas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVenta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVentas(): void {
    this.eventSubscriber = this.eventManager.subscribe('ventaListModification', () => this.loadAll());
  }

  delete(venta: IVenta): void {
    const modalRef = this.modalService.open(VentaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.venta = venta;
  }
}
