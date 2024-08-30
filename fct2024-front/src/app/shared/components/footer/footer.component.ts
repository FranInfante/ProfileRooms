import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ASSET_URLS, FOOTER_IMGS } from '../constants';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  ASSET_URLS: typeof ASSET_URLS = ASSET_URLS;
  footerImgUrl = FOOTER_IMGS.footerImgUrl;

}
