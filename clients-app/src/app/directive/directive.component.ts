import { Component } from '@angular/core';

@Component({
  selector: 'app-directive',
  templateUrl: './directive.component.html',
  styleUrls: ['./directive.component.css']
})
export class DirectiveComponent {
  
  listCourses: string[] = ['TypeScript', 'JavaScript', 'Java SE', 'C#', 'PHP'];
  
  enable: boolean = true;

  setEnable(): void {
    this.enable = !this.enable
  }
}
