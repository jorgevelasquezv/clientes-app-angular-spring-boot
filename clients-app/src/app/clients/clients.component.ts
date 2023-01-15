import { Component } from '@angular/core';
import { Client } from './client';
import { ClientService } from './client.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
})
export class ClientsComponent {
  clients: Client[] = [] ;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.clientService
      .getClients()
      .subscribe((clients) => (this.clients = clients));
  }

  public delete(client: Client): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-outline-success mx-2',
        cancelButton: 'btn btn-outline-danger',
      },
      buttonsStyling: false,
    });

    swalWithBootstrapButtons
      .fire({
        title: 'Esta seguro?',
        text: `¿Seguro que desea eliminar el cliente ${client.name} ${client.lastname}`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Si, eliminar!',
        cancelButtonText: 'No, cancelar!',
        reverseButtons: true,
      })
      .then((result) => {
        if (result.isConfirmed) {
          this.clientService.delete(client.id).subscribe((response) => {
            this.clients = this.clients?.filter(c => c !== client);
            swalWithBootstrapButtons.fire(
              'Cliente Eliminado!',
              `El cliente ${client.name} fue eliminado exitosamente.`,
              'success'
            );
          });
        }
      });
  }
}
