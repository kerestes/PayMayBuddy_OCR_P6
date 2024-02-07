import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login403Component } from './login-403.component';

xdescribe('Login403Component', () => {
  let component: Login403Component;
  let fixture: ComponentFixture<Login403Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login403Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Login403Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
