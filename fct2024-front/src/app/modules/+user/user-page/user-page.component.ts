import { Component } from '@angular/core';
import { UserComponent } from '../user-information/user-information.component';

@Component({
  selector: 'app-user-page',
  standalone: true,
  imports: [UserComponent],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent {

}
