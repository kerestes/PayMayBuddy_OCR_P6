import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetirerComponent } from './retirer.component';

describe('RetirerComponent', () => {
  let component: RetirerComponent;
  let fixture: ComponentFixture<RetirerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RetirerComponent]
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
