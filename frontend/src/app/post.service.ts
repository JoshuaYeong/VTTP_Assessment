import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, } from "rxjs";
import { Post, PostResponse } from "./models";

@Injectable()
export class PostService{

  constructor(private http: HttpClient) { }

  createPost(post: Post, file: File | Blob): Promise<PostResponse> {

    console.log('::::: createPost() is called')

    // const httpHeaders = new HttpHeaders()
    //   .set("Content-Type", "multipart/form-data")
    //   .set("Accept", "application/json");

      const formData = new FormData()
      formData.set("name", post['name'])
      formData.set("email", post['email'])
      formData.set("phone", post['phone'])
      formData.set("title", post['title'])
      formData.set("description", post['description'])
      formData.set('myfile', file)

      console.log(':::::formData:', formData)

      return firstValueFrom(
        this.http.post<PostResponse>('/api/posting', formData)
      )
  }

}
