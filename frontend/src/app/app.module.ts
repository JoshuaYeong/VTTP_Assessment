import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PostComponent } from './component/post.component';
import { ConfirmationComponent } from './component/confirmation.component';

@NgModule({
  declarations: [
    AppComponent,
    PostComponent,
    ConfirmationComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
