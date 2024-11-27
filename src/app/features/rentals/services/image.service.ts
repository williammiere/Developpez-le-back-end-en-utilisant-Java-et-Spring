import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient,
    private sanitizer: DomSanitizer
  ) { }

  getImage(filename: string): Observable<Blob> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(filename, { headers, responseType: 'blob' });
  }

  loadImage(picture: string): Observable<SafeUrl | null> {
    return this.getImage(picture).pipe(
      map((blob) => {
        const objectURL = URL.createObjectURL(blob);
        const safeUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        return safeUrl;
      }),
      catchError((error) => {
        console.error('Erreur lors du chargement de l\'image', error);
        return of(null);
      })
    );
  }
}