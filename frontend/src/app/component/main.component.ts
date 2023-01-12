import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Post, PostResponse } from '../models';
import { PostService } from '../post.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  @ViewChild('toUpload')
  toUpload!: ElementRef

  form!: FormGroup

  newPost!: any

  constructor(private fb:FormBuilder, private router: Router, private postSvc: PostService) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      // name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      // email: this.fb.control<string>('', [Validators.required, Validators.email]),

      name: this.fb.control<string>('',),
      email: this.fb.control<string>('',),
      phone: this.fb.control<string>(''),
      title: this.fb.control<string>(''),
      description: this.fb.control<string>(''),
      file: this.fb.control<any>(''),
    })
  }

  processForm() {
    console.log(":::::processForm() is called")
    const myFile = this.toUpload.nativeElement.files[0]
    const values = this.form.value as Post;
    console.log(':::::Post: ', values)
    console.log(':::::File: ', myFile)
    this.postSvc.createPost(values, myFile)
      .then(result => {
        console.log(":::::RESULT: ", result)
        this.router.navigate(['api/posting/result'])
        this.postSvc.shareData(result)
        this.newPost = result
      }).catch(error => {
        console.error(":::::Main Comp: ", error)
      })
  }

  clear() {
    console.log(":::::Clear() is called")
    this.form.reset();
  }

}
