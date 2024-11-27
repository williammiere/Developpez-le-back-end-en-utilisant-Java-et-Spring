import { Component, Input } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { RentalsService } from '../../services/rentals.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImageService } from '../../services/image.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public rentals$ = this.rentalsService.all();
  public images: { [key: number]: SafeUrl | null } = {};

  constructor(
    private sessionService: SessionService,
    private rentalsService: RentalsService,
    private imageService: ImageService,
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }

  ngOnInit(): void {
    this.rentals$.subscribe(rentalsResponse => {
      rentalsResponse?.rentals.forEach(rental => {
        this.loadImage(rental.id, rental.picture);
      });
    });
  }

  loadImage(rentalId: number, picture: string): void {
    this.imageService.loadImage(picture).subscribe({
      next: (safeUrl) => {
        this.images[rentalId] = safeUrl;
      },
      error: (error) => {
        console.error('Erreur lors du chargement de l\'image', error);
        this.images[rentalId] = null;
      }
    });
  }
}
