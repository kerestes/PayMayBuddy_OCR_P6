import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { LoginService } from './services/loginService/login.service';
import {HttpClientTestingModule} from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing';

describe('AppComponent', () => {



  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[LoginService],
      imports: [AppComponent, HttpClientTestingModule, RouterTestingModule],
    }).compileComponents();
    const loginService:LoginService = TestBed.inject(LoginService);
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render Login', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    let verify:boolean = false;
    const els = compiled.querySelectorAll('span');
    els.forEach(element => {
      if(element.textContent == 'Login')
        verify = true;
    })
    expect(verify).toBeTrue();
  });

  it('should render Logout', () => {
    const fixture = TestBed.createComponent(AppComponent);
    let component = fixture.componentInstance;
    component.login = true;
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('button > span');
    els.forEach(element => {
      if(element.textContent?.trim() == 'Logout')
        verify = true;
    })

    expect(verify).toBeTrue();
  });

  it('should made a lougout', fakeAsync(()=>{
    const fixture = TestBed.createComponent(AppComponent);
    let component = fixture.componentInstance;

    spyOn(component, 'logout');

    component.login = true;
    fixture.detectChanges();

    let button = fixture.debugElement.nativeElement.querySelector('#logoutButton');
    button.click();
    tick();

    expect(component.logout).toHaveBeenCalled();
  }))
});
