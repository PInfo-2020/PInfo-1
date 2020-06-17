import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-upload-photo',
  templateUrl: './upload-photo.component.html',
  styleUrls: ['./upload-photo.component.css'],
})
export class UploadPhotoComponent implements OnInit {
  fileData: File = null;
  previewUrl: any = null;
  fileUploadProgress: string = null;
  uploadedFilePath: string = null;
  constructor(private http: HttpClient, public keycloak: KeycloakService) {}

  ngOnInit() {}

  fileProgress(fileInput: any) {
    this.fileData = fileInput.target.files[0] as File;
    this.preview();
  }

  preview() {
    // Show preview
    const mimeType = this.fileData.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(this.fileData);
    reader.onload = (_event) => {
      this.previewUrl = reader.result;
    };
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('files', this.fileData);

    this.http
      .post(
        'https://api.imgbb.com/1/upload?key=cba68c7b28823915fd3ddeea7cd241f4',
        formData,
        {
          headers: new HttpHeaders(
            {'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
             rejectUnauthorized: 'false' }),
          reportProgress: false,
          observe: 'events'
        })
      .subscribe((events) => {
        if (events.type === HttpEventType.Response) {
          console.log(events.body);
        }
      });
    // ajout de la classe JS à HTML
    document.querySelector('html').classList.add('js');

    // initialisation des variables
    const fileInput = document.querySelector('.input-file');
    const button = document.querySelector('.input-file-trigger');
    const theReturn = document.querySelector('.file-return');

    // action lorsque le label est cliqué
    // tslint:disable-next-line: only-arrow-functions
    button.addEventListener('click', function(event) {
      (fileInput  as HTMLElement).focus();
      return false;
    });

    // affiche un retour visuel dès que input:file change
    fileInput.addEventListener('change', function(event) {
      theReturn.innerHTML = this.value;
    });
  }
}
