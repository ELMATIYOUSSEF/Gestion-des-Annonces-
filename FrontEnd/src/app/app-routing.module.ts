import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './core/guards/auth.guard';
import { LayoutComponent } from './layouts/layout.component';
import { Page404Component } from './extrapages/page404/page404.component';
import {HomePageComponent} from './home-page/home-page.component';
import {NoAuthGuard} from './core/guards/no-auth.guard';
import {AdminGuard} from './core/guards/admin.guard';
import {ThankYouComponent} from './thank-you/thank-you.component';

const routes: Routes = [
  { path: 'thank-you', component: ThankYouComponent , canActivate: [AuthGuard]},
  { path: 'account', loadChildren: () => import('./account/account.module').then(m => m.AccountModule), canActivate: [NoAuthGuard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '', component: LayoutComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule), canActivate: [AdminGuard] },
  { path: 'pages', loadChildren: () => import('./extrapages/extrapages.module').then(m => m.ExtrapagesModule), canActivate: [AuthGuard] },
  { path: 'home' , component: HomePageComponent },
  { path: '**', component: Page404Component },
];


@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
