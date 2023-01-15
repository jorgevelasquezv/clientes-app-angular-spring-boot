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

  public title: String = 'Crear cliente';

  constructor(
    private clientService: ClientService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }
  
  ngOnInit() {
    this.loadClient()
  }

  public loadClient(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params['id'];
      if (id) {
        this.clientService.getClient(id).subscribe(client => this.client = client);
      }
    })
  }

  public create(): void {
    this.clientService.create(this.client).subscribe((client) => {
      this.router.navigate(['/clients']);
      Swal.fire('Nuevo cliente', `Cliente ${client.name}`, 'success');
    });
  }

  public update(): void {
    this.clientService.update(this.client).subscribe(client => {
      this.router.navigate(['/clients']);
      Swal.fire('Cliente actualizado', `Cliente ${client.name} actualizado con exito`, 'success')
    })
  }
}
