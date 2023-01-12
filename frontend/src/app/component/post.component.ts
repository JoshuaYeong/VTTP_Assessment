import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { PostResponse } from '../models';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  sub$!: Subscription
  newPost!: any

  imageURL: string = ""
  postId: string = ""
  postDate: string = ""
  name: string = ""
  // email: string = ""
  // phone: string = ""
  // title: string = ""
  // description: string = ""

  constructor(private activatedRoute: ActivatedRoute, private postSvc: PostService) { }

  ngOnInit(): void {
    this.postSvc.share$.subscribe(
      res => {
        console.log(res)
        this.newPost = res
        console.log(':::newPost:',this.newPost)
      }
    )
  }

}
