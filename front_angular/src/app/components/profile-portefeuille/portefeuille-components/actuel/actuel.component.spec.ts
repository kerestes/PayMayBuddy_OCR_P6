import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActuelComponent } from './actuel.component';

describe('ActuelComponent', () => {
  let component: ActuelComponent;
  let fixture: ComponentFixture<ActuelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActuelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ActuelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
