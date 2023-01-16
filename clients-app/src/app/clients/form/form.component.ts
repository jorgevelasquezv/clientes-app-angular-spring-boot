import { Component } from '@angular/core';
import { Client } from '../client';
import { ClientService } from '../client.service';
import { Router, ActivatedRoute } from '@angular/router';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: [],
})
export class FormComponent {
  public client: Client = new Client();

  public title: string = 'Crear cliente';

  public errors: string[] = [];
  
  constructor(
    private clientService: ClientService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.loadClient();
  }

  public loadClient(): void {
    this.activatedRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clientService
          .getClient(id)
          .subscribe((response) => (this.client = response.cliente));
      }
    });
  }

  public create(): void {
    this.clientService.create(this.client).subscribe({
      next: (response) => {
        this.router.navigate(['/clients']);
        Swal.fire(
          'Nuevo cliente',
          `${response.mensaje} ${response.cliente.name}`,
          'success'
        );
      },
      error: (err) => {
        this.errors = err.error.errors as string[];
      }
    });
  }

  public update(): void {
    this.clientService.update(this.client).subscribe({
      next: (response) => {
        this.router.navigate(['/clients']);
        Swal.fire(
          'Cliente actualizado',
          `${response.mensaje} ${response.cliente.name}`,
          'success'
        );
      },
      error: (err) => {
        this.errors = err.error.errors as string[];
      },
      complete: () => console.info('complete'),
    });
  }

}
