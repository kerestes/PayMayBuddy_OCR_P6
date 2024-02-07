import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileConfigComponent } from './profile-config.component';
import { ProfilService } from '../../services/profilService/profil.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('ProfileConfigComponent', () => {
  let component: ProfileConfigComponent;
  let fixture: ComponentFixture<ProfileConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [ProfilService],
      imports: [ProfileConfigComponent, HttpClientTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
