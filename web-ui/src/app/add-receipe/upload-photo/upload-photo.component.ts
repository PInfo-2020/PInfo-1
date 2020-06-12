import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
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

    this.fileUploadProgress = '0%';

    this.http
      .post(
        'https://us-central1-tutorial-e6ea7.cloudfunctions.net/fileUpload',
        formData,
        {
          reportProgress: true,
          observe: 'events',
        }
      )
      .subscribe((events) => {
        if (events.type === HttpEventType.UploadProgress) {
          this.fileUploadProgress =
            Math.round((events.loaded / events.total) * 100) + '%';
          console.log(this.fileUploadProgress);
        } else if (events.type === HttpEventType.Response) {
          this.fileUploadProgress = '';
          console.log(events.body);
          alert('SUCCESS !!');
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
