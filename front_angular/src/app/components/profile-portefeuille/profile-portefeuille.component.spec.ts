import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePortFeuilleComponent } from './profile-portefeuille.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('ProfilePortFeuilleComponent', () => {
  let component: ProfilePortFeuilleComponent;
  let fixture: ComponentFixture<ProfilePortFeuilleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilePortFeuilleComponent, RouterTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfilePortFeuilleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
