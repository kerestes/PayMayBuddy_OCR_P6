import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuddysComponent } from './buddys.component';

describe('BuddysComponent', () => {
  let component: BuddysComponent;
  let fixture: ComponentFixture<BuddysComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuddysComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuddysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
