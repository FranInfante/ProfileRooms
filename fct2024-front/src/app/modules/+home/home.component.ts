import { AfterViewInit, Component } from '@angular/core';
import { ASSET_HOMEIMAGES } from '../../shared/components/constants';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})export class HomeComponent implements AfterViewInit {
  assetUrls = ASSET_HOMEIMAGES;

  ngAfterViewInit() {
    let mybutton = document.getElementById("btn-back-to-top") as HTMLElement;

    window.onscroll = function () {
      scrollFunction();
    };

    function scrollFunction() {
      if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.classList.add("visible");
      } else {
        mybutton.classList.remove("visible");
      }
    }

    mybutton.addEventListener("click", backToTop);

    function backToTop() {
      document.body.scrollTop = 0;
      document.documentElement.scrollTop = 0;
    }
  }
}
