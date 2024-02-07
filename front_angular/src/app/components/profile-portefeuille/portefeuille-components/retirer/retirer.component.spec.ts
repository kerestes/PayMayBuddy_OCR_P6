import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetirerComponent } from './retirer.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('RetirerComponent', () => {
  let component: RetirerComponent;
  let fixture: ComponentFixture<RetirerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RetirerComponent, HttpClientTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RetirerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
