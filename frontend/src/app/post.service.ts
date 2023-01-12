import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Observable, Subject, } from "rxjs";
import { Post, PostResponse } from "./models";

@Injectable()
export class PostService{

  share: Subject<any> = new Subject();
  share$: Observable<any> = this.share.asObservable();

  constructor(private http: HttpClient) { }

  shareData(data: any){

    console.log('::::: shareData() is called')
    this.share.next(data);
  }

  createPost(post: Post, file: File | Blob): Promise<any> {

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

      return firstValueFrom(
        this.http.post<any>('/api/posting', formData)
      )
  }

  putPosting(postingId: string) {

    console.log('::::: putPosting() is called')

    const httpHeaders = new HttpHeaders()
    .set('Content-type', 'application/json')
    .set('Accept', 'application/json')

    return firstValueFrom(
      this.http.put<any>(`/api/posting/${ postingId}`, {headers: httpHeaders})
    )
  }

}
