import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { LoginService } from '../../services/loginService/login.service';
import { RegisterService } from '../../services/registerService/register.service';
import { MatDialog } from '@angular/material/dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[
        LoginService, RegisterService, MatDialog,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              queryParamMap: { get: () => "12345612"}
            }
          }
        }
      ],
      imports: [HomeComponent, HttpClientTestingModule, RouterTestingModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call activateRegistration on ngOnnit', () => {

    spyOn(component, 'activateRegistration');
    component.ngOnInit();
    expect(component.activateRegistration).toHaveBeenCalled();

  });

  it('should render page as false login', () => {

    component.login = false
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('p');
    els.forEach(element => {
      if(element.textContent?.includes("Voulez-vous faire partie des meilleiurs amis ?"))
        verify = true
    })

    expect(verify).toBeTrue();
  });

  it('should render page as false true', () => {

    component.login = true
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('p');
    els.forEach(element => {
      if(element.textContent?.includes("Soyez le bienvenu !!!"))
        verify = true
    })

    expect(verify).toBeTrue();
  });
});
