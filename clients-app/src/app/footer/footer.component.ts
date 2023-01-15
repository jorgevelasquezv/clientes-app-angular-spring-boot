import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent {
  autor: any = {
    name: 'Jorge',
    lastname: 'Velásquez',
  };
}
