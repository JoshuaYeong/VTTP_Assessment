import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { PostComponent } from './component/post.component';
import { ConfirmationComponent } from './component/confirmation.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './component/main.component';
import { PostService } from './post.service';

const appPath: Routes = [
  { path: '', component: MainComponent },
  { path: 'api/posting', component: PostComponent },
  { path: 'api/posting/:postingId', component: ConfirmationComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    PostComponent,
    ConfirmationComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appPath, { useHash: true}),

  ],
  providers: [ PostService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
