import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotConfirmedComponent } from './depot-confirmed.component';

xdescribe('DepotConfirmedComponent', () => {
  let component: DepotConfirmedComponent;
  let fixture: ComponentFixture<DepotConfirmedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepotConfirmedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepotConfirmedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
