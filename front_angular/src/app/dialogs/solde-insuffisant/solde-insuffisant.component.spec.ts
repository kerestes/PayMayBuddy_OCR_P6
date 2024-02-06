import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoldeInsuffisantComponent } from './solde-insuffisant.component';

describe('SoldeInsuffisantComponent', () => {
  let component: SoldeInsuffisantComponent;
  let fixture: ComponentFixture<SoldeInsuffisantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoldeInsuffisantComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SoldeInsuffisantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
