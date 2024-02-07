import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetraitConfirmedComponent } from './retrait-confirmed.component';

xdescribe('RetraitConfirmedComponent', () => {
  let component: RetraitConfirmedComponent;
  let fixture: ComponentFixture<RetraitConfirmedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RetraitConfirmedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RetraitConfirmedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
