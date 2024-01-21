import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmeRegistrationComponent } from './confirme-registration.component';

describe('ConfirmeRegistrationComponent', () => {
  let component: ConfirmeRegistrationComponent;
  let fixture: ComponentFixture<ConfirmeRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmeRegistrationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmeRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
