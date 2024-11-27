import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Rental } from 'src/app/features/rentals/interfaces/rental.interface';
import { SessionService } from 'src/app/services/session.service';
import { MessageRequest } from '../../interfaces/api/messageRequest.interface';
import { MessageResponse } from '../../interfaces/api/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { RentalsService } from '../../services/rentals.service';
import { SafeUrl } from '@angular/platform-browser';
import { ImageService } from '../../services/image.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public rental: Rental | undefined;
  public images: { [key: number]: SafeUrl | null } = {};


  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private rentalsService: RentalsService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar,
    private imageService: ImageService) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.rentalsService
      .detail(id)
      .subscribe((rental: Rental) => {this.rental = rental;
        this.loadImage(Number(id), this.rental.picture);
      });
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    const message = {
      rental_id: this.rental!.id,
      user_id: this.sessionService.user?.id,
      message: this.messageForm.value.message
    } as MessageRequest;

    this.messagesService.send(message).subscribe(
      (messageResponse: MessageResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.message, "Close", { duration: 3000 });
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
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
