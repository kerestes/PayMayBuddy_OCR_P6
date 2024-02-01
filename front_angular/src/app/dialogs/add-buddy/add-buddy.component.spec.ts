import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBuddyComponent } from './add-buddy.component';

describe('AddBuddyComponent', () => {
  let component: AddBuddyComponent;
  let fixture: ComponentFixture<AddBuddyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddBuddyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddBuddyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
