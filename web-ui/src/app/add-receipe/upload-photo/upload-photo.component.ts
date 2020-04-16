import { Component, OnInit } from '@angular/core';
import { UploaderService } from './uploader.service';

@Component({
  selector: 'app-upload-photo',
  templateUrl: './upload-photo.component.html',
  styleUrls: ['./upload-photo.component.css']
})

export class UploadPhotoComponent implements OnInit {

  progress: number;
  infoMessage: any;
  isUploading = false;
  file: File;

  imageUrl: string | ArrayBuffer =
    'https://bulma.io/images/placeholders/480x480.png';
  fileName = 'No file selected';

  constructor(private uploader: UploaderService) {}

  ngOnInit() {
    this.uploader.progressSource.subscribe(progress => {
      this.progress = progress;
    });
  }

  onChange(file: File) {
    if (file) {
      this.fileName = file.name;
      this.file = file;

      const reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = event => {
        this.imageUrl = reader.result;
      };
    }
  }

  onUpload() {
    this.infoMessage = null;
    this.progress = 0;
    this.isUploading = true;

    this.uploader.upload(this.file).subscribe(message => {
      this.isUploading = false;
      this.infoMessage = message;
    });
  }

}
