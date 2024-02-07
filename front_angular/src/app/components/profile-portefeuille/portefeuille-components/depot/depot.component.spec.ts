import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotComponent } from './depot.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('DepotComponent', () => {
  let component: DepotComponent;
  let fixture: ComponentFixture<DepotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepotComponent, HttpClientTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
